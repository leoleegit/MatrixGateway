package com.matrix.gateway.config;

import com.matrix.core.model.rest.MyGsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public HttpMessageConverters HttpMessageConverters(){
        return new HttpMessageConverters(new MyGsonHttpMessageConverter());
    }
}
