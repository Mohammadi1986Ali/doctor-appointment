package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.dto.security.Role;
import com.blubank.doctorappointment.entity.SecurityRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface RoleMapper {
    @Mapping(target = "roleName", source = "name")
    SecurityRole map(Role role);

    Set<SecurityRole> map(Set<Role> role);
}
