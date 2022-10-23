package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.entity.SecurityUser;
import com.blubank.doctorappointment.exceptions.AppointmentAlreadyTakenException;
import com.blubank.doctorappointment.exceptions.AppointmentDeletionNotAllowedForNotOwnerException;
import com.blubank.doctorappointment.exceptions.AppointmentDeletionNotAllowedForTakenException;
import com.blubank.doctorappointment.exceptions.AppointmentNotFoundException;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.repository.UserRepository;
import com.blubank.doctorappointment.service.mapper.AppointmentMapper;
import com.blubank.doctorappointment.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final UserRepository userRepository;
    private final AppointmentMapper mapper;
    private final UserMapper userMapper;

    @Override
    public List<AppointmentDto> addOpenAppointments(LocalDateTime start, LocalDateTime end, String username) {
        List<Appointment> appointments = new ArrayList<>();
        LocalDateTime from = start;
        LocalDateTime to = from.plusMinutes(30);

        SecurityUser doctor = userRepository.findByUsername(username);

        while (to.isBefore(end)) {
            Appointment appointment = new Appointment();
            appointment.setStart(from);
            appointment.setEnd(to);
            appointment.setCreatedBy(doctor);
            appointments.add(appointment);

            from = to;
            to = from.plusMinutes(30);
        }
        return mapper.map(repository.saveAll(appointments));
    }

    @Override
    public List<AppointmentDto> findOpenAppointments() {
        return mapper.map(repository.findAllByTakenByIsNull());
    }

    @Override
    public List<AppointmentDto> findAppointmentsTakenBy(String username) {
        SecurityUser patient = userRepository.findByUsername(username);
        return(mapper.map(repository.findAllByTakenBy(patient)));
    }

    @Override
    public List<AppointmentDto> findAppointmentsCreatedBy(String username) {
        SecurityUser doctor = userRepository.findByUsername(username);
        List<Appointment> appointments = repository.findAllByCreatedBy(doctor);
        return mapper.map(appointments);
    }

    @Override
    @Transactional
    public AppointmentDto takeAppointment(Long appointmentId, String username, String patientName, String patientPhone) {
        Appointment appointment = repository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        if (appointment.getTakenBy() != null)
            throw new AppointmentAlreadyTakenException(appointmentId);

        SecurityUser patient = userRepository.findByUsername(username);
        appointment.setTakenBy(patient);
        appointment.setPatientName(patientName);
        appointment.setPatientPhone(patientPhone);
        return mapper.map(appointment);
    }

    @Override
    public void deleteOpenAppointment(Long appointmentId, String username) {
        Appointment appointment = repository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        SecurityUser doctor = userRepository.findByUsername(username);

        if (appointment.getTakenBy() != null)
            throw new AppointmentDeletionNotAllowedForTakenException();

        if (!appointment.getCreatedBy().getId().equals(doctor.getId()))
            throw new AppointmentDeletionNotAllowedForNotOwnerException();

        repository.deleteById(appointmentId);
    }
}
