package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.generated.v1.api.AppointmentsApi;
import com.blubank.doctorappointment.generated.v1.model.FullAppointmentInfo;
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
public class AppointmentsResource implements AppointmentsApi {

    private final AppointmentService appointmentService;
    private final FullAppointmentInfoMapper mapper;

    @Override
    public ResponseEntity<List<FullAppointmentInfo>> getAppointments() {
        List<AppointmentDto> appointments = appointmentService.findAppointmentsCreatedBy(getLoggedInUser());
        return ResponseEntity.ok(mapper.map(appointments));
    }

    String getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
