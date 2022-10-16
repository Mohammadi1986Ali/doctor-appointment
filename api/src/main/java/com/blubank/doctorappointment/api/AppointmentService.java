package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.dto.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> save(LocalDateTime start, LocalDateTime end);
}