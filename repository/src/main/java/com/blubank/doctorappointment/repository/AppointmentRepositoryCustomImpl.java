package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentRepositoryCustomImpl implements AppointmentRepositoryCustom {

    @Autowired
    EntityManager em;

    @Override
    public List<Appointment> findAllOpenAppointment() {
        List<Appointment> appointments = em.createQuery("select t from Appointment t", Appointment.class).getResultList();
        return appointments.stream().filter(p -> p.getPatient() != null).collect(Collectors.toList());
    }
}
