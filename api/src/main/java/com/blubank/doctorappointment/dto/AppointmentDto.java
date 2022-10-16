package com.blubank.doctorappointment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AppointmentDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private PatientDto patient;
}
