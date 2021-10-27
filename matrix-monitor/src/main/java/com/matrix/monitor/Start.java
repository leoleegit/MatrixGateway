package com.matrix.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
