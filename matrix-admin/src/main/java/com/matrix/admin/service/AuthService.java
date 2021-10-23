package com.matrix.admin.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.google.gson.JsonObject;
import com.matrix.core.config.JWTConfig;
import com.matrix.core.constants.ApiCode;
import com.matrix.core.constants.CS;
import com.matrix.core.jwt.JWTPayload;
import com.matrix.core.jwt.JWTUtils;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.rest.req.AuthenticationReq;
import com.matrix.core.model.rest.resp.AuthenticationResp;
import com.matrix.core.model.rest.resp.JsonBuilder;
import com.matrix.core.model.security.AuthUserDetails;
import com.matrix.core.model.security.UserInfo;
import com.matrix.core.util.RedisUtil;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccessPermissionService accessPermissionService;

    @Resource
    private AuthenticationManager authenticationManager;

    public Resp<AuthenticationResp> login(AuthenticationReq req) throws UnsupportedEncodingException {
        String principal = StrUtil.format("{}:{}",req.getAuthType(),req.getUsername());
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(principal, req.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(upToken);
        }catch (UsernameNotFoundException | BadCredentialsException exception){
            return Resp.fail(ApiCode.ERROR_1002);
        }
        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
        UserInfo userInfo = authUserDetails.getUserInfo();
        String cacheID = UUID.fastUUID().toString();
        String id      = CS.cacheKey(CS.CacheKey.TOKEN,cacheID);

        //save cache
        authUserDetails.setCacheKey(id);
        RedisUtil.setObject(id,authUserDetails,jwtConfig.getExpiresIn()*60);

        long now       = System.currentTimeMillis();
        JWTPayload jwtPayload = new JWTPayload();
        jwtPayload.setPayloadID(id);
        jwtPayload.setCreatedAt(new Date(now));
        jwtPayload.setUserid(userInfo.getUsername());
        jwtPayload.setExpiresIn(new Date(now + jwtConfig.getExpiresIn()*60*1000l));
        String token = JWTUtils.generateToken(jwtPayload,jwtConfig.getSecret());
        AuthenticationResp authenticationResp = new AuthenticationResp();
        authenticationResp.setCreatedAt(jwtPayload.getCreatedAt());
        authenticationResp.setExpiresIn(jwtPayload.getExpiresIn());
        authenticationResp.setToken(token);
        authenticationResp.setUser(userInfo);

        List<AccessPermission> accessPermissionList = accessPermissionService.queryListByUser(userInfo.getId());
        if(accessPermissionList!=null && accessPermissionList.size()>0){
            List<String> permissions = new ArrayList<>(accessPermissionList.size());
            for (AccessPermission accessPermission: accessPermissionList) {
                permissions.add(accessPermission.getCode());
            }
            authenticationResp.setPermissions(permissions.toArray(new String[permissions.size()]));
        }
        return Resp.success(authenticationResp);
    }

    public Resp<JsonObject> captcha(){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(90, 40, 4, 80);
        lineCaptcha.createCode();

        //Redis cache
        String cacheID = UUID.fastUUID().toString();
        RedisUtil.setObject(CS.cacheKey(CS.CacheKey.CAPTCHA,cacheID), lineCaptcha.getCode(), 60 ); // 1 min

        JsonObject jsonObject = JsonBuilder.builder()
                .add("imageBase64Data",lineCaptcha.getImageBase64Data())
                .add("imageID",cacheID).build();

        return Resp.success(jsonObject);
    }
}
