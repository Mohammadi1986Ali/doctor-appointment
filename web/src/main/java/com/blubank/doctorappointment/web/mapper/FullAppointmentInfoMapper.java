package com.blubank.doctorappointment.web.mapper;

import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.generated.v1.model.FullAppointmentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FullAppointmentInfoMapper {
    List<FullAppointmentInfo> map(List<AppointmentDto> appointments);

    @Mapping(target = "patient.name", source = "patientName")
    @Mapping(target = "patient.phone", source = "patientPhone")
    FullAppointmentInfo map(AppointmentDto appointment);
}
