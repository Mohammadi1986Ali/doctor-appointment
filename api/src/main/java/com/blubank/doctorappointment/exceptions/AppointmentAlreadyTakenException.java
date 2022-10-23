package com.blubank.doctorappointment.exceptions;

import lombok.Getter;

@Getter
public class AppointmentAlreadyTakenException extends RuntimeException {

    private final Long appointmentId;

    public AppointmentAlreadyTakenException(Long appointmentId) {
        super();
        this.appointmentId = appointmentId;
    }

    public AppointmentAlreadyTakenException(String message, Long appointmentId) {
        super(message);
        this.appointmentId = appointmentId;
    }
}
