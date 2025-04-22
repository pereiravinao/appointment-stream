package com.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseModel {
    private String name;
    private String description;
    private String status;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private String type;
    private User createdBy;
    private User consumer;

    public void setCreatedBy(Long createdById) {
        this.createdBy = new User(createdById);
    }

    public void setConsumer(Long consumerId) {
        this.consumer = new User(consumerId);
    }

}
