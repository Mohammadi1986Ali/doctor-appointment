package com.blubank.doctorappointment.web.mapper;

import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.generated.v1.model.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentModelMapper {
    AppointmentDto map(Appointment appointment);
    Appointment map(AppointmentDto appointment);
    List<Appointment> map(List<AppointmentDto> appointments);
}
