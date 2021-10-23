package com.matrix.media.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
@ConfigurationProperties()
public class MediaServerConfig {

    @Value("${matrix.media.accessKey}")
    private String accessKey;

    @Value("${matrix.media.secretKey}")
    private String secretKey;

    @Value("${matrix.media.endpoint}")
    private String endpoint;

    @Value("${matrix.media.bucket}")
    private String bucket;

    @Value("${matrix.media.download.endpoint}")
    private String downloadEndpoint;

}
