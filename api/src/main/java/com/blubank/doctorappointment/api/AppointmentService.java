package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.dto.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> addOpenAppointments(LocalDateTime start, LocalDateTime end, String username);

    List<AppointmentDto> findOpenAppointments();

    List<AppointmentDto> findAppointmentsTakenBy(String username);

    List<AppointmentDto> findAppointmentsCreatedBy(String username);

    AppointmentDto takeAppointment(Long appointmentId, String username, String patientName, String patientPhone);

    void deleteOpenAppointment(Long appointmentId, String username);
}