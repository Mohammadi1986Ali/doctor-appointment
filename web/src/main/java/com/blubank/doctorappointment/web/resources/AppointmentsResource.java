package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.exceptions.InvalidAppointmentTimeException;
import com.blubank.doctorappointment.generated.v1.api.AppointmentsApi;
import com.blubank.doctorappointment.generated.v1.model.Appointment;
import com.blubank.doctorappointment.web.mapper.AppointmentModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AppointmentsResource implements AppointmentsApi {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentModelMapper mapper;

    @Override
    public ResponseEntity<List<Appointment>> addNewAppointments(Appointment appointment) {
        if (appointment.getStart().isAfter(appointment.getEnd())) {
            throw new InvalidAppointmentTimeException("Start date is after end date");
        }
        List<AppointmentDto> saveAppointments = appointmentService.save(
                appointment.getStart(), appointment.getEnd()
        );
        return ResponseEntity.ok(mapper.map(saveAppointments));
    }

    @Override
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<AppointmentDto> allAppointments = appointmentService.findAllOpenAppointment();
        return ResponseEntity.ok(mapper.map(allAppointments));
    }
}
