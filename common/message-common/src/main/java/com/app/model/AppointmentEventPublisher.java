package com.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentEventPublisher {
    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private String type;
    private Long createdById;
    private User consumer;

}
