package com.app.service.interfaces;

import java.util.List;

import com.app.model.AppointmentEvent;

public interface AppointmentEventService {

    void save(AppointmentEvent appointmentEvent);

    List<AppointmentEvent> findAll();

    AppointmentEvent findById(Long id); 
}
    