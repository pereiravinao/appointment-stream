package com.app.entity;

import java.time.LocalDateTime;

import com.app.model.Appointment;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity extends BaseEntity {

    private String name;
    private String description;
    private String status;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private String type;
    private Long createdById;
    private Long consumerId;

    public AppointmentEntity(Appointment appointment) {
        this.name = appointment.getName() != null ? appointment.getName() : this.name;
        this.description = appointment.getDescription() != null ? appointment.getDescription() : this.description;
        this.status = appointment.getStatus() != null ? appointment.getStatus() : this.status;
        this.starTime = appointment.getStarTime() != null ? appointment.getStarTime() : this.starTime;
        this.endTime = appointment.getEndTime() != null ? appointment.getEndTime() : this.endTime;
        this.type = appointment.getType() != null ? appointment.getType() : this.type;
        if (appointment.getCreatedBy() != null) {
            this.createdById = appointment.getCreatedBy().getId() != null ? appointment.getCreatedBy().getId()
                    : this.createdById;
        }
        if (appointment.getConsumer() != null) {
            this.consumerId = appointment.getConsumer().getId() != null ? appointment.getConsumer().getId()
                    : this.consumerId;
        }
    }

    public Appointment toModel() {
        var appointment = Appointment.builder()
                .name(this.name)
                .description(this.description)
                .status(this.status)
                .starTime(this.starTime)
                .endTime(this.endTime)
                .type(this.type)
                .build();
        appointment.setId(super.getId());
        appointment.setOwnerId(super.getOwnerId());
        appointment.setCreatedAt(super.getCreatedAt());
        appointment.setUpdatedAt(super.getUpdatedAt());
        appointment.setVersion(super.getVersion());
        appointment.setCreatedBy(this.createdById);
        appointment.setConsumer(this.consumerId);
        return appointment;
    }
}
