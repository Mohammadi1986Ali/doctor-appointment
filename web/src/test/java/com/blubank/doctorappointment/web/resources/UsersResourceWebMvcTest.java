package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.UserService;
import com.blubank.doctorappointment.config.SecurityConfig;
import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.web.jwt.JwtTokenUtil;
import com.blubank.doctorappointment.web.mapper.EncryptedPasswordMapper;
import com.blubank.doctorappointment.web.mapper.UserModelMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.blubank.doctorappointment.utils.MockDataLoader.CreateUserRequestEnum.CREATE_USER_REQUEST;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersResource.class)
@Import({SecurityConfig.class, JwtTokenUtil.class,
        UserModelMapperImpl.class, EncryptedPasswordMapper.class})
class UsersResourceWebMvcTest {

    public static final String ENDPOINT_USERS = "/api/v1/users";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService service;

    @MockBean
    Logger logger;

    @Test
    void shouldCreateUser() throws Exception {
        final UUID generatedUserId = UUID.randomUUID();
        final String username = "ali";

        Mockito.when(service.save(any())).thenReturn(User.builder()
                .userId(generatedUserId).username(username).build());

        mockMvc.perform(post(ENDPOINT_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_USER_REQUEST.jsonString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(generatedUserId.toString()))
                .andExpect(jsonPath("$.username").value(username));
    }
}