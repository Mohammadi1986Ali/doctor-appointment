package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.generated.v1.api.DoctorApi;
import com.blubank.doctorappointment.generated.v1.model.Appointment;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public class DoctorResource implements DoctorApi {

    @Override
    public ResponseEntity<List<Appointment>> getReservedAppointment(BigDecimal doctorId) {
        return null;
    }
}
