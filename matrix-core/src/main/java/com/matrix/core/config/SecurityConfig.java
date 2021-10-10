package com.matrix.core.config;

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

    @Value("#{'${matrix.security.web.ignoring:[]}'.split(',')}")
    private String[] ignoring;
}
