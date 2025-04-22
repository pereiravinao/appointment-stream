package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.entity.AppointmentEventEntity;
import com.app.model.AppointmentEvent;
import com.app.repository.AppointmentEventRepository;
import com.app.service.interfaces.AppointmentEventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentEventBaseServiceImpl implements AppointmentEventService {

    private final AppointmentEventRepository appointmentEventRepository;

    @Override
    public void save(AppointmentEvent appointmentEvent) {
        appointmentEventRepository.save(new AppointmentEventEntity(appointmentEvent));
    }

    @Override
    public List<AppointmentEvent> findAll() {
        return appointmentEventRepository.findAllBase().stream().map(AppointmentEventEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentEvent findById(Long id) {
        return appointmentEventRepository.findByIdBase(id).map(AppointmentEventEntity::toModel)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado	"));
    }

}
