package com.blubank.doctorappointment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    @ManyToOne
    private SecurityUser takenBy;

    @ManyToOne
    private SecurityUser createdBy;

    private String patientName;

    private String patientPhone;

    @Version
    private Long version;
}
