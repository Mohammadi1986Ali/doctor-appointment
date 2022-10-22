package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.service.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper mapper;

    @Autowired
    private AppointmentRepository repository;

    @Override
    public List<AppointmentDto> save(LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointments = new ArrayList<>();
        LocalDateTime from = start;
        LocalDateTime to = from.plusMinutes(30);

        while (to.isBefore(end)) {
            Appointment appointment = new Appointment();
            appointment.setStart(from);
            appointment.setEnd(to);
            appointments.add(appointment);

            from = to;
            to = from.plusMinutes(30);
        }
        return mapper.map(repository.saveAll(appointments));
    }

    @Override
    public List<AppointmentDto> findAllOpenAppointment() {
        return mapper.map(repository.findAllOpenAppointment());
    }
}
