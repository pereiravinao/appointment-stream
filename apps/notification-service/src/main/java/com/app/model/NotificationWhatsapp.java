package com.app.model;

import com.app.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationWhatsapp extends Notification {

    private String to;
    private String message;

    public NotificationWhatsapp(String to, String message) {
        super(NotificationType.WHATSAPP);
        this.to = to;
        this.message = message;
    }

    public NotificationWhatsapp() {
        super(NotificationType.WHATSAPP);
    }

}
