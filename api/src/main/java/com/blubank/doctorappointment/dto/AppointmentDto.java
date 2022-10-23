package com.blubank.doctorappointment.dto;

import com.blubank.doctorappointment.dto.security.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private User takenBy;
    private User createdBy;
    private String patientName;
    private String patientPhone;
}
