package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.dto.error.ErrorMessage;
import com.blubank.doctorappointment.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidAppointmentTimeException.class})
    public void badRequestHandler() {

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AppointmentDeletionNotAllowedForTakenException.class})
    public ErrorMessage handleAppointmentDeletionNotAllowedForTakenException() {
        return new ErrorMessage("Appointment deletion not allowed for those taken by patient");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AppointmentDeletionNotAllowedForNotOwnerException.class)
    public ErrorMessage handleAppointmentDeletionNotAllowedForNotOwnerException() {
        return new ErrorMessage("The appointment is created by another doctor and you are not allowed to delete it");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({AppointmentNotFoundException.class})
    public ErrorMessage handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        return new ErrorMessage(String.format("Appointment %s not found", e.getAppointmentId()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AppointmentAlreadyTakenException.class})
    public ErrorMessage handleAppointmentNotFoundException(AppointmentAlreadyTakenException e) {
        return new ErrorMessage(String.format("Appointment %s already taken", e.getAppointmentId()));
    }
}
