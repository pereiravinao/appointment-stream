package com.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private String name;
    private String exchange;
    private String routingKey;

}
