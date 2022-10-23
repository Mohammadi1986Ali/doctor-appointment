package com.blubank.doctorappointment.web.mapper;

import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.generated.v1.model.CreateUserRequest;
import com.blubank.doctorappointment.generated.v1.model.CreateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = EncryptedPasswordMapper.class)
public interface UserModelMapper {

    @Mapping(target = "encryptedPassword", source = "password", qualifiedBy = EncodedMapping.class)
    User map(CreateUserRequest createUserRequest);

    CreateUserResponse map(User user);
}
