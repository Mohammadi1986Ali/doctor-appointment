package com.blubank.doctorappointment.web.controller;

import com.blubank.doctorappointment.integration.IntegrationTest;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.ParseException;

import static com.blubank.doctorappointment.utils.MockDataLoader.LoginRequestEnum.LOGIN_REQUEST;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class LoginControllerIntegrationTest {

    public static final String ENDPOINT_LOGIN = "/login";
    public static final String TOKEN = "token";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Environment env;

    @MockBean
    Logger logger;

    @Test
    @Sql("/sql-data/add-security-user.sql")
    void shouldCreateToken_whenSuccessfulLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOGIN_REQUEST.jsonString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(header().string(TOKEN,
                        is(notNullValue()))
                ).andReturn();
        String jwt = mvcResult.getResponse().getHeader(TOKEN);
        assertTrue(isValidToken(jwt));
    }

    private boolean isValidToken(String token) throws ParseException, JOSEException {
        SignedJWT jwt = SignedJWT.parse(token);
        MACVerifier verifier = new MACVerifier(env.getProperty("token.secret"));
        return jwt.verify(verifier);
    }
}