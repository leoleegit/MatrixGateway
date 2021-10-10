package com.matrix.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
public class DefaultConfig {
    @Value("${matrix.default.password:Aa123456}")
    private String password;

    @Value("${matrix.default.avatarUrl:./logo.svg}")
    private String avatarUrl;
}
