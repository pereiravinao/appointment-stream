package com.app.resolver;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.app.model.Appointment;
import com.app.service.interfaces.AppointmentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentQueryResolver {

    private final AppointmentService appointmentService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Appointment findById(@Argument Long id) {
        return appointmentService.findById(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<Appointment> findAll() {
        return appointmentService.findAll();
    }
}