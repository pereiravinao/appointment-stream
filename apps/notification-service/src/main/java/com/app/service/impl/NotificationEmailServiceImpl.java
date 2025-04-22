package com.app.service.impl;

import org.springframework.stereotype.Service;

import com.app.model.Notification;
import com.app.service.interfaces.NotificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationEmailServiceImpl implements NotificationService {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending email notification to " + notification.toString());
    }

}
