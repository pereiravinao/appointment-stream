package com.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "deepgram")
public class DeepgramProperties {
    private String apiKey;
    private String apiUrl;
}
