package com.blubank.doctorappointment.web.mapper;

import com.blubank.doctorappointment.dto.security.Role;
import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.generated.v1.model.CreateUserRequest;
import com.blubank.doctorappointment.generated.v1.model.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserModelMapperTest {

    @InjectMocks
    UserModelMapper mapper= Mappers.getMapper(UserModelMapper.class);

    @Mock
    EncryptedPasswordMapper encryptedPasswordMapper;

    @Test
    void shouldMapUserAndRoles(){
        final String username = "ali";
        final

        CreateUserRequest createUserRequest = new CreateUserRequest().username(username)
                .roles(List.of(new UserRole().name("ADMIN"), new UserRole().name("USER")));

        User user = mapper.map(createUserRequest);

        Assertions.assertThat( user.getUsername()).isEqualTo(username);
        Assertions.assertThat(user.getRoles()).extracting(Role::getName).contains("ADMIN", "USER");
    }
}