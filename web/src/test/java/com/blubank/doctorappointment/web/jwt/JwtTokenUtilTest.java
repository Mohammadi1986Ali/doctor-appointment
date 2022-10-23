package com.blubank.doctorappointment.web.jwt;

import com.blubank.doctorappointment.integration.IntegrationTest;
import com.blubank.doctorappointment.utils.MockDataLoader;
import com.blubank.doctorappointment.web.security.SpringSecurity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;

@IntegrationTest
class JwtTokenUtilTest extends SpringSecurity {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    Logger logger;

    @Test
    void generateToken() throws Exception {
        var userDto = MockDataLoader.SecurityUser.USER_WITH_ROLES_AND_PRIVILEGES.readApiModel();

        final User user = new User(userDto.getUsername(),
                userDto.getEncryptedPassword(),  // passwordEncoder.encode("123456")
                getAuthorities(userDto.getRoles()));

        String token = jwtTokenUtil.generateAccessToken(user);

        System.out.println(token);
    }
}