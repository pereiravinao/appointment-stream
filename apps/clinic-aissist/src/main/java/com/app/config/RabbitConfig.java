package com.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.properties.RabbitProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final RabbitProperties rabbitProperties;

    @Bean
    public TopicExchange minioExchange() {
        return new TopicExchange(rabbitProperties.getExchange(), true, false);
    }

    @Bean
    public Queue minioQueue() {
        return new Queue(rabbitProperties.getName(), true, false, false);
    }

    @Bean
    public Binding minioBinding(TopicExchange minioExchange, Queue minioQueue) {
        return BindingBuilder
                .bind(minioQueue)
                .to(minioExchange)
                .with(rabbitProperties.getRoutingKey());
    }

}
