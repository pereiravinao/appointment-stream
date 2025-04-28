package com.app.entity;

import java.time.LocalDateTime;

import com.app.model.AppointmentEvent;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_appointment_event")
@NoArgsConstructor
public class AppointmentEventEntity extends BaseEntity {

    private Long appointmentId;
    private String name;
    private String status;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private String type;
    private Long createdById;
    private Long consumerId;

    public AppointmentEvent toModel() {
        var appointmentEvent = AppointmentEvent.builder()
                .appointmentId(appointmentId)
                .name(name)
                .status(status)
                .starTime(starTime)
                .endTime(endTime)
                .type(type)
                .createdById(createdById)
                .build();
        appointmentEvent.setConsumer(consumerId);
        return appointmentEvent;
    }

    public AppointmentEventEntity(AppointmentEvent appointmentEvent) {
        this.appointmentId = appointmentEvent.getAppointmentId();
        this.name = appointmentEvent.getName();
        this.status = appointmentEvent.getStatus();
        this.starTime = appointmentEvent.getStarTime();
        this.endTime = appointmentEvent.getEndTime();
        this.type = appointmentEvent.getType();
        this.createdById = appointmentEvent.getCreatedById();
        this.consumerId = appointmentEvent.getConsumer().getId();
    }
}
