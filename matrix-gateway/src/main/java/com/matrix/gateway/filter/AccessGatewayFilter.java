package com.matrix.gateway.filter;

import cn.hutool.core.thread.ThreadUtil;
import com.matrix.gateway.config.SecurityConfig;
import com.matrix.gateway.model.req.CheckAccessPermissionReq;
import com.matrix.gateway.model.resp.CheckAccessPermissionResp;
import com.matrix.gateway.service.PermissionService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityConfig securityConfig;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        String method     = request.getMethod().toString();
        ServerHttpRequest.Builder mutate = request.mutate();

        if(isIgnore(requestUri)){
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        }

        String token = token(request);
        if(token==null){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        CheckAccessPermissionResp checkAccessPermissionResp = checkPermission(new CheckAccessPermissionReq(method,requestUri),token);
        if(checkAccessPermissionResp.getIsAuth()){
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        }else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }

    private CheckAccessPermissionResp checkPermission(CheckAccessPermissionReq checkAccessPermissionReq, String token) throws ExecutionException, InterruptedException {
        return ThreadUtil.execAsync(new Callable<CheckAccessPermissionResp>() {
            @Override
            public CheckAccessPermissionResp call() throws Exception {
                return  permissionService.checkPermission(checkAccessPermissionReq,token);
            }
        }).get();
    }

    private String token(ServerHttpRequest request){

        String authToken = null;
        List<String> strings = request.getHeaders().get(securityConfig.getTokenHeader());
        if(strings!=null && strings.size()>0)
            authToken = strings.get(0);
        if(StringUtils.isEmpty(authToken)){
            authToken = request.getQueryParams().getFirst(securityConfig.getTokenHeader());
        }else if(StringUtils.isEmpty(authToken) && securityConfig.isCookieTokenEnable()){
            if (request.getCookies() != null) {

                for (HttpCookie cookie : request.getCookies().get(securityConfig.getTokenHeader())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }
        return authToken;
    }

    private boolean isIgnore(String requestUri){
        ArrayList<PathPattern> pathPatterns = pathPatterns();
        Optional<PathPattern> optionalPathPattern = pathPatterns.stream().filter((pattern) -> {
            return pattern.matches(PathContainer.parsePath(requestUri));
        }).findFirst();
       return optionalPathPattern.isPresent();
    }

    private ArrayList<PathPattern> pathPatterns(){
        final ArrayList<PathPattern> pathPatterns = new ArrayList();
        String[] ignoring = securityConfig.getIgnoring();
        if(ignoring!=null){
            for (String pattern : ignoring){
                PathPattern pathPattern = PathPatternParser.defaultInstance.parse(pattern.trim());
                pathPatterns.add(pathPattern);
            }
        }

        return pathPatterns;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
