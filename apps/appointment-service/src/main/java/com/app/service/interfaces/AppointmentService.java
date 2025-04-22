package com.app.service.interfaces;

import java.util.List;

import com.app.model.Appointment;

public interface AppointmentService {

    Appointment create(Appointment appointment);

    List<Appointment> findAll();

    Appointment findById(Long id);

    void delete(Long id);

    Appointment update(Appointment appointment);

}
