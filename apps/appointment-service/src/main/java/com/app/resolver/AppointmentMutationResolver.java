package com.app.resolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.app.model.Appointment;
import com.app.service.interfaces.AppointmentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentMutationResolver {

    private final AppointmentService appointmentService;

    @MutationMapping
    public Appointment create(@Argument Appointment appointment) {
        return appointmentService.create(appointment);
    }

    @MutationMapping
    public Appointment update(@Argument Appointment appointment) {
        return appointmentService.update(appointment);
    }

    @MutationMapping
    public void delete(@Argument Long id) {
        appointmentService.delete(id);
    }
}
