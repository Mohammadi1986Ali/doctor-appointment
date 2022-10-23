package com.blubank.doctorappointment.exceptions;

import lombok.Getter;

@Getter
public class AppointmentNotFoundException extends RuntimeException {

    private final Long appointmentId;

    public AppointmentNotFoundException(Long appointmentId) {
        super();
        this.appointmentId = appointmentId;
    }

    public AppointmentNotFoundException(String message, Long appointmentId) {
        super(message);
        this.appointmentId = appointmentId;
    }
}
