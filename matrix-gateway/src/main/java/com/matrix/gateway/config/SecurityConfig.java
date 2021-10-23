package com.matrix.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
public class SecurityConfig {

    @Value("${matrix.security.web.tokenHeader:Authorization}")
    private String tokenHeader;

    @Value("${matrix.security.web.cookieToken:false}")
    private boolean cookieTokenEnable;

    @Value("${matrix.security.web.payloadHeader:PayloadID}")
    private String payloadHeader;

    @Value("#{'${matrix.security.web.ignoring:[]}'.split(',')}")
    private String[] ignoring;
}
