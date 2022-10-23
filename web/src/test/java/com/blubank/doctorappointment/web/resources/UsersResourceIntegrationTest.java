package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.integration.IntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.blubank.doctorappointment.utils.MockDataLoader.CreateUserRequestEnum.CREATE_USER_REQUEST;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class UsersResourceIntegrationTest {

    public static final String ENDPOINT_USERS = "/api/v1/users";

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateUser() throws Exception {
        final UUID generatedUserId = UUID.randomUUID();
        final String username = "ali";

        mockMvc.perform(post(ENDPOINT_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_USER_REQUEST.jsonString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(is(notNullValue())))
                .andExpect(jsonPath("$.username").value(username));
    }
}