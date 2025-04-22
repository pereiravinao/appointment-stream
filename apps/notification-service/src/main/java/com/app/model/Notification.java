package com.app.model;

import java.time.LocalDateTime;

import com.app.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Notification {

    private String name;
    private String description;
    private String status;
    private LocalDateTime starTime;
    private User consumer;
    private NotificationType type;

    public Notification(NotificationType type) {
        this.type = type;
    }

    public Notification() {
    }

}
