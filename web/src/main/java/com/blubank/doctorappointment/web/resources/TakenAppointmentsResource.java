package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.generated.v1.api.TakenAppointmentsApi;
import com.blubank.doctorappointment.generated.v1.model.BasicAppointmentInfo;
import com.blubank.doctorappointment.web.mapper.BasicAppointmentInfoMapper;
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
public class TakenAppointmentsResource implements TakenAppointmentsApi {

    private final AppointmentService service;
    private final BasicAppointmentInfoMapper mapper;

    @Override
    public ResponseEntity<List<BasicAppointmentInfo>> getTakenAppointments() {
        List<AppointmentDto> takenAppointments = service.findAppointmentsTakenBy(getLoggedInUser());
        return ResponseEntity.ok(mapper.map(takenAppointments));
    }

    String getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
