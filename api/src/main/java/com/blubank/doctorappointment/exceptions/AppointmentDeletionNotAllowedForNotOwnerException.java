package com.blubank.doctorappointment.exceptions;

public class AppointmentDeletionNotAllowedForNotOwnerException extends RuntimeException{
    public AppointmentDeletionNotAllowedForNotOwnerException() {
        super();
    }

    public AppointmentDeletionNotAllowedForNotOwnerException(String message) {
        super(message);
    }
}
