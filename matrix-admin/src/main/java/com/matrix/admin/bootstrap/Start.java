package com.matrix.admin.bootstrap;

import com.matrix.core.constants.CS;
import com.matrix.core.util.RedisUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.matrix.*.config",
        "com.matrix.service.impl",
        "com.matrix.admin.controller",
        "com.matrix.admin.service",
        "com.matrix.core.security",
        "com.matrix.admin.security"})
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}


