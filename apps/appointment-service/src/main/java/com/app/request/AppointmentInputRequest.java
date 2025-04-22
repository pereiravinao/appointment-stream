package com.app.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.graphql.data.method.annotation.SchemaMapping;

import com.app.model.Appointment;
import com.app.utils.SecurityUtils;

import lombok.Data;

@SchemaMapping("AppointmentInput")
@Data
public class AppointmentInputRequest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    private String name;
    private String description;
    private String status;
    private String type;
    private Long consumerId;

    private LocalDateTime starTime;
    private LocalDateTime endTime;

    public void setStarTime(String starTime) {
        this.starTime = LocalDateTime.parse(starTime, formatter);
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalDateTime.parse(endTime, formatter);
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = Long.parseLong(consumerId);
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
        appointment.setConsumer(this.consumerId);
        appointment.setCreatedBy(SecurityUtils.getCurrentUserAuth().getId());
        appointment.setOwnerId(SecurityUtils.getCurrentUserAuth().getOwnerId());
        return appointment;
    }
}
