package com.app.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppointmentEvent {

    private Long id;
    private Long appointmentId;
    private String name;
    private String status;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private String type;
    private Long createdById;
    private User consumer;

    public void setConsumer(Long consumerId) {
        this.consumer = User.builder()
                .id(consumerId)
                .build();
    }
}
