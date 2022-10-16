package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.exceptions.InvalidAppointmentTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidAppointmentTimeException.class})
    public void badRequestHandler(){
    }
}
