package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByTakenByIsNull();

    List<Appointment> findAllByTakenBy(SecurityUser patient);

    List<Appointment> findAllByCreatedBy(SecurityUser doctor);

}
