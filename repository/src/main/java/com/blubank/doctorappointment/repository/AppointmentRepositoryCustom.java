package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.Appointment;

import java.util.List;

public interface AppointmentRepositoryCustom {
    List<Appointment> findAllOpenAppointment();
}
