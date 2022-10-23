package com.blubank.doctorappointment.web.mapper;

import com.blubank.doctorappointment.dto.AppointmentDto;
import com.blubank.doctorappointment.generated.v1.model.BasicAppointmentInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BasicAppointmentInfoMapper {

    BasicAppointmentInfo map(AppointmentDto appointment);

    List<BasicAppointmentInfo> map(List<AppointmentDto> appointments);
}
