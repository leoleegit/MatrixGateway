package com.matrix.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
@ConfigurationProperties()
public class JWTConfig {
    @Value("${matrix.security.jwt.secret:37620098-8cc2-4fae-a5c8-c36765ebacf4}")
    private String secret;

    @Value("${matrix.security.jwt.expiresIn:120}")
    private Long expiresIn;
}
