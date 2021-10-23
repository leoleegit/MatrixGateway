package com.matrix.gateway.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages={
        "com.matrix.gateway.service"
})
@ComponentScan(basePackages = {
        "com.matrix.gateway.config",
        "com.matrix.gateway.filter",
        "com.matrix.gateway.service" }
)
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
