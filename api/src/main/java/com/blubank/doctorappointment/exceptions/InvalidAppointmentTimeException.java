package com.blubank.doctorappointment.exceptions;

public class InvalidAppointmentTimeException extends RuntimeException{
    public InvalidAppointmentTimeException() {
        super();
    }

    public InvalidAppointmentTimeException(String message) {
        super(message);
    }
}
