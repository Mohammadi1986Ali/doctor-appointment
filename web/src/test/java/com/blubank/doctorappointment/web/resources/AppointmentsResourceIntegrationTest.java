package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.UserService;
import com.blubank.doctorappointment.integration.IntegrationTest;
import com.blubank.doctorappointment.web.jwt.JwtTokenUtil;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
class AppointmentsResourceIntegrationTest {

    public static final String ENDPOINT_APPOINTMENTS = "/api/v1/appointments";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() throws JOSEException {
    }


}