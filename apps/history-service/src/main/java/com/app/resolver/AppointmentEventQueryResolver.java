package com.app.resolver;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.app.model.AppointmentEvent;
import com.app.service.interfaces.AppointmentEventService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentEventQueryResolver {

    private final AppointmentEventService appointmentEventService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public AppointmentEvent findById(@Argument Long id) {
        return appointmentEventService.findById(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<AppointmentEvent> findAll() {
        return appointmentEventService.findAll();
    }
}