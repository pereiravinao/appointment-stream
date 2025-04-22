package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.entity.AppointmentEntity;
import com.app.model.Appointment;
import com.app.repository.AppointmentRepository;
import com.app.service.interfaces.AppointmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentBaseServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.saveBase(new AppointmentEntity(appointment)).toModel();
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll().stream().map(AppointmentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).map(AppointmentEntity::toModel).orElse(null);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.saveBase(new AppointmentEntity(appointment)).toModel();
    }

}
