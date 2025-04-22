package com.app.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.app.model.AppointmentEventPublisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class AppointmentPublisher {
    private final AmqpTemplate amqpTemplate;

    public void publish(AppointmentEventPublisher appointmentEvent) {
        try {
            amqpTemplate.convertAndSend("appointment-exchange", "appointment-routing-key", appointmentEvent);
            log.info("Appointment event published: {}", appointmentEvent);
        } catch (Exception e) {
            log.error("Error publishing appointment event: {}", e.getMessage());
        }
    }

}