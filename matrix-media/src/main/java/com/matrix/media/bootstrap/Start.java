package com.matrix.media.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.matrix.*.config",
        "com.matrix.service.impl",
        "com.matrix.media.controller",
        "com.matrix.media.service",
        "com.matrix.core.security",
        "com.matrix.media.security"},
        basePackageClasses = {
                com.matrix.media.MediaSwitch.class
})
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
