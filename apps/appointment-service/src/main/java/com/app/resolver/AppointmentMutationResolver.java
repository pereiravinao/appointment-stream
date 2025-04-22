package com.app.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.app.model.Appointment;
import com.app.request.AppointmentInputRequest;
import com.app.service.interfaces.AppointmentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentMutationResolver {

    private final AppointmentService appointmentService;

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'OPERATOR')")
    public Appointment create(@Argument AppointmentInputRequest appointment) {
        return appointmentService.create(appointment.toModel());
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'OPERATOR')")
    public Appointment update(@Argument AppointmentInputRequest appointment) {
        return appointmentService.update(appointment.toModel());
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void delete(@Argument Long id) {
        appointmentService.delete(id);
    }
}
