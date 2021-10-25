package com.matrix.core.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.matrix.core.config.JWTConfig;
import com.matrix.core.config.SecurityConfig;
import com.matrix.core.jwt.JWTPayload;
import com.matrix.core.jwt.JWTUtils;
import com.matrix.core.model.security.AuthUserDetails;
import com.matrix.core.cache.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final static String BEARER = "bearer ";
    private SecurityConfig securityConfig;
    private JWTConfig jwtConfig;

    public AuthenticationTokenFilter(SecurityConfig securityConfig, JWTConfig jwtConfig) {
        this.securityConfig = securityConfig;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest httpServletRequest,
                                    javax.servlet.http.HttpServletResponse httpServletResponse,
                                    javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        AuthUserDetails authUserDetails = commonFilter(httpServletRequest);
        if(authUserDetails == null){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUserDetails, null, authUserDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private AuthUserDetails commonFilter(HttpServletRequest request){
        /**
         * Gateway 重写 PayloadHeader 授权
         * **/
        String payloadID = request.getHeader(securityConfig.getPayloadHeader());
        if(!StringUtils.isEmpty(payloadID)){
            AuthUserDetails authUserDetails = RedisUtil.getObject(payloadID,AuthUserDetails.class);
            return authUserDetails;
        }

        String authToken = request.getHeader(securityConfig.getTokenHeader());
        if(StringUtils.isEmpty(authToken)){
            authToken = request.getParameter(securityConfig.getTokenHeader());
        }else if(StringUtils.isEmpty(authToken) && securityConfig.isCookieTokenEnable()){
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(securityConfig.getTokenHeader())) {
                        authToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if(StringUtils.isEmpty(authToken)){
            return null;
        }
        if(authToken.toLowerCase().startsWith(BEARER))
            authToken = authToken.substring(BEARER.length());
        try {
            JWTPayload jwtPayload = JWTUtils.parseToken(authToken,jwtConfig.getSecret());
            if(jwtPayload!=null && jwtPayload.getPayloadID()!=null){
                AuthUserDetails authUserDetails = RedisUtil.getObject(jwtPayload.getPayloadID(),AuthUserDetails.class);
                return authUserDetails;
            }
        } catch (UnsupportedEncodingException | TokenExpiredException e) {
            return null;
        }
        return null;
    }
}
