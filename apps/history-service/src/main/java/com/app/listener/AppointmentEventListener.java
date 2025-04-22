package com.app.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.app.model.AppointmentEvent;
import com.app.service.interfaces.AppointmentEventService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppointmentEventListener {

    private final AppointmentEventService appointmentEventService;

    @RabbitListener(queues = "appointment-queue")
    public void receiveMessage(AppointmentEvent appointmentEvent) {
        appointmentEventService.save(appointmentEvent);
    }
}
