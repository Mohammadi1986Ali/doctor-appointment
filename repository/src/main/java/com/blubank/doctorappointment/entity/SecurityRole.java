package com.blubank.doctorappointment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_ROLES_ROLENAME", columnNames = {"rolename"}),
})
public class SecurityRole {
    @Id
    @GeneratedValue
    private UUID id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<SecurityUser> users;
}
