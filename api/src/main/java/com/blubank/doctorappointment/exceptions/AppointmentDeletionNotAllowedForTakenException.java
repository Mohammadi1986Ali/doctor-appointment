package com.blubank.doctorappointment.exceptions;

public class AppointmentDeletionNotAllowedForTakenException extends RuntimeException{
    public AppointmentDeletionNotAllowedForTakenException() {
        super();
    }

    public AppointmentDeletionNotAllowedForTakenException(String message) {
        super(message);
    }
}
