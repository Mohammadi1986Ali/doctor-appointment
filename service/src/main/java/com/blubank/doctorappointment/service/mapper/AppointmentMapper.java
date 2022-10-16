package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.entity.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = PatientMapper.class)
public interface AppointmentMapper {
    Appointment map(AppointmentDto appointmentDto);

    AppointmentDto map(Appointment appointment);

    List<AppointmentDto> map(List<Appointment> appointments);
}
