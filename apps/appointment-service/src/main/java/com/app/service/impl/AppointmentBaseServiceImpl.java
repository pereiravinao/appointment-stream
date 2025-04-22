package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.entity.AppointmentEntity;
import com.app.model.Appointment;
import com.app.model.AppointmentEventPublisher;
import com.app.model.User;
import com.app.publisher.AppointmentPublisher;
import com.app.repository.AppointmentRepository;
import com.app.service.UserFeignServiceImpl;
import com.app.service.interfaces.AppointmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentBaseServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentPublisher appointmentPublisher;
    private final UserFeignServiceImpl userFeignService;

    @Override
    public Appointment create(Appointment appointment) {
        var savedAppointment = appointmentRepository.saveBase(new AppointmentEntity(appointment));
        var appointmentModel = savedAppointment.toModel();
        publish(appointmentModel);
        return appointmentModel;
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAllBase().stream().map(AppointmentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findByIdBase(id).map(AppointmentEntity::toModel).orElseThrow(
                () -> new RuntimeException("Agendamento não encontrado"));
    }

    @Override
    public void delete(Long id) {
        var appointment = findById(id);
        appointmentRepository.deleteById(id);
        publish(appointment);
    }

    @Override
    public Appointment update(Appointment requestAppointment) {
        var appointment = findById(requestAppointment.getId());
        var appointmentModel = appointment.update(requestAppointment);
        appointmentModel = appointmentRepository.saveBase(new AppointmentEntity(appointmentModel)).toModel();
        publish(appointmentModel);
        return appointmentModel;
    }

    private void publish(Appointment appointment) {
        var appointmentEvent = AppointmentEventPublisher.builder()
                .id(appointment.getId())
                .name(appointment.getName())
                .description(appointment.getDescription())
                .status(appointment.getStatus())
                .starTime(appointment.getStarTime())
                .endTime(appointment.getEndTime())
                .build();
        if (appointment.getConsumer() != null) {
            appointmentEvent.setConsumer(getConsumerDetails(appointment.getConsumer().getId()));
        }
        appointmentPublisher.publish(appointmentEvent);
    }

    private User getConsumerDetails(Long id) {
        var user = userFeignService.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user;
    }

}
