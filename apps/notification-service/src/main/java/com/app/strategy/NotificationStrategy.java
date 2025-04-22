package com.app.strategy;

import org.springframework.stereotype.Component;

import com.app.model.AppointmentEventPublisher;
import com.app.model.NotificationEmail;
import com.app.model.NotificationWhatsapp;
import com.app.service.impl.NotificationEmailServiceImpl;
import com.app.service.impl.NotificationWhatsappServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class NotificationStrategy {

    private final NotificationEmailServiceImpl notificationEmailService;
    private final NotificationWhatsappServiceImpl notificationWhatsappService;

    public void sendNotification(AppointmentEventPublisher appointmentEvent) {
        if (appointmentEvent.getConsumer() == null) {
            log.error("Consumer not found");
            return;
        }
        if (appointmentEvent.getConsumer().getEmail() != null) {
            notificationEmailService.send(new NotificationEmail(appointmentEvent.getConsumer().getEmail(),
                    "Subject", "Body"));
        }
        if (appointmentEvent.getConsumer().getPhone() != null) {
            notificationWhatsappService.send(new NotificationWhatsapp(appointmentEvent.getConsumer().getPhone(),
                    "Body"));
        }
    }
}