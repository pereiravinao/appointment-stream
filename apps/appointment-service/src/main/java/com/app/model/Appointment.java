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

    public Appointment update(Appointment appointment) {
        this.name = appointment.getName();
        this.description = appointment.getDescription();
        this.status = appointment.getStatus();
        this.starTime = appointment.getStarTime();
        this.endTime = appointment.getEndTime();
        this.type = appointment.getType();
        this.createdBy = appointment.getCreatedBy();
        this.consumer = appointment.getConsumer();
        return this;
    }

}
