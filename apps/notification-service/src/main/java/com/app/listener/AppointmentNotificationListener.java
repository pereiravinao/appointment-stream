package com.app.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.model.AppointmentEventPublisher;
import com.app.strategy.NotificationStrategy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class AppointmentNotificationListener {

    private final NotificationStrategy notificationStrategy;

    @RabbitListener(queues = "appointment-queue")
    public void receiveMessage(AppointmentEventPublisher appointmentEvent) {
        try {
            log.info("Received appointment notification: {}", appointmentEvent);
            notificationStrategy.sendNotification(appointmentEvent);
        } catch (Exception e) {
            log.error("Error sending appointment notification: {}", e.getMessage());
        }
    }
}