package com.blubank.doctorappointment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PatientDto {
    private String name;
    private String mobileNo;
}
