package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.entity.SecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "password", source = "encryptedPassword")
    SecurityUser map(User user);

    @Mapping(target = "encryptedPassword", source = "password")
    @Mapping(target = "userId", source = "id")
    User map(SecurityUser user);
}
