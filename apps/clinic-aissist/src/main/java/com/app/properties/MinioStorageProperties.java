package com.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio.storage")
public class MinioStorageProperties {
    private String bucket;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String region;
}
