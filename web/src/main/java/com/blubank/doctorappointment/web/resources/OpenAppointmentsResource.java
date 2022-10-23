package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.exceptions.InvalidAppointmentTimeException;
import com.blubank.doctorappointment.generated.v1.api.OpenAppointmentsApi;
import com.blubank.doctorappointment.generated.v1.model.BasicAppointmentInfo;
import com.blubank.doctorappointment.generated.v1.model.CreateAppointmentRequest;
import com.blubank.doctorappointment.generated.v1.model.FullAppointmentInfo;
import com.blubank.doctorappointment.generated.v1.model.Patient;
import com.blubank.doctorappointment.web.mapper.BasicAppointmentInfoMapper;
import com.blubank.doctorappointment.web.mapper.FullAppointmentInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OpenAppointmentsResource implements OpenAppointmentsApi {

    private final AppointmentService service;
    private final BasicAppointmentInfoMapper mapper;
    private final FullAppointmentInfoMapper fullAppointmentInfoMapper;

    @Override
    public ResponseEntity<List<BasicAppointmentInfo>> addNewAppointments(CreateAppointmentRequest createAppointmentRequest) {
        // todo: should be the same day

        if (createAppointmentRequest.getStart().isAfter(createAppointmentRequest.getEnd()))
            throw new InvalidAppointmentTimeException("Start date is after end date");

        List<AppointmentDto> savedAppointments = service.addOpenAppointments(
                createAppointmentRequest.getStart(), createAppointmentRequest.getEnd(), getLoggedInUser());
        return ResponseEntity.ok(mapper.map(savedAppointments));
    }

    @Override
    public ResponseEntity<List<BasicAppointmentInfo>> getOpenAppointments() {
        List<AppointmentDto> appointments = service.findOpenAppointments();
        return ResponseEntity.ok(mapper.map(appointments));
    }

    @Override
    public ResponseEntity<Void> deleteOpenAppointment(Long appointmentId) {
        service.deleteOpenAppointment(appointmentId, getLoggedInUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FullAppointmentInfo> takeAppointment(Long appointmentId, Patient body) {
        AppointmentDto appointmentDto = service.takeAppointment(appointmentId, getLoggedInUser(), body.getName(), body.getPhone());
        return ResponseEntity.ok(fullAppointmentInfoMapper.map(appointmentDto));
    }

    String getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
