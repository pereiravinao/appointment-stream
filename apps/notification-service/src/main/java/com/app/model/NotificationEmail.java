package com.app.model;

import com.app.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEmail extends Notification {

    private String to;
    private String subject;
    private String body;

    public NotificationEmail(String to, String subject, String body) {
        super(NotificationType.EMAIL);
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public NotificationEmail() {
        super(NotificationType.EMAIL);
    }
}
