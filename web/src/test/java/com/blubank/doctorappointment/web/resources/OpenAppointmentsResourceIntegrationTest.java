package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.UserService;
import com.blubank.doctorappointment.integration.IntegrationTest;
import com.blubank.doctorappointment.web.jwt.JwtTokenUtil;
import net.minidev.json.JSONArray;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.blubank.doctorappointment.utils.MockDataLoader.CreateAppointmentRequest.CREATE_APPOINTMENT_REQUEST;
import static com.blubank.doctorappointment.utils.MockDataLoader.CreateAppointmentRequest.CREATE_APPOINTMENT_REQUEST_PERIOD_LESS_THAN_30_MINUTES;
import static com.blubank.doctorappointment.utils.MockDataLoader.PatientEnum.PATIENT_CONTACT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@IntegrationTest
class OpenAppointmentsResourceIntegrationTest {

    public static final String ENDPOINT_OPEN_APPOINTMENTS = "/api/v1/open-appointments";
    public static final String ENDPOINT_OPEN_APPOINTMENTS_APPOINTMENT_ID = "/api/v1/open-appointments/{appointmentId}";
    private static final String USER_WITH_PATIENT_ROLE = "patient-1";
    private static final String USER_WITH_OTHER_ROLE = "patient-3";
    private static final String USER_WITH_NO_ROLE = "patient-4";
    private static final String USER_WITH_DOCTOR_ROLE = "doctor";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Test
    @Sql("/sql-data/add-doctor-user.sql")
    void givenDoctorRole_whenPeriodIsLessThan30Minutes_thenReturnEmptyList() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_DOCTOR_ROLE));

        mockMvc.perform(post(ENDPOINT_OPEN_APPOINTMENTS)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_APPOINTMENT_REQUEST_PERIOD_LESS_THAN_30_MINUTES.jsonString())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    @Sql("/sql-data/add-doctor-user.sql")
    void givenDoctorRole_whenCreateOpenAppointments_thenReturnListOfAppointments() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_DOCTOR_ROLE));

        mockMvc.perform(post(ENDPOINT_OPEN_APPOINTMENTS)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_APPOINTMENT_REQUEST.jsonString())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(JSONArray.class)))
                .andExpect(jsonPath("$", hasSize(19)));
    }

    @Test
    @Sql("/sql-data/add-patient-users.sql")
    void givenPatientRole_whenGetOpenAppointments_thenSuccessfulResponse() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_PATIENT_ROLE));

        mockMvc.perform(get(ENDPOINT_OPEN_APPOINTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenPatientRole_whenViewOpenAppointments_thenReturnListOfOpenAppointment() {

    }

    @Test
    @Sql("/sql-data/add-patient-users.sql")
    void givenNoPatientRole_whenGetOpenAppointments_thenForbiddenResponse() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_OTHER_ROLE));

        mockMvc.perform(get(ENDPOINT_OPEN_APPOINTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("/sql-data/add-patient-users.sql")
    void givenNoRole_whenGetOpenAppointments_thenForbiddenResponse() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_NO_ROLE));

        mockMvc.perform(get(ENDPOINT_OPEN_APPOINTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("/sql-data/add-patient-users.sql")
    void givenUnauthorizedUser_whenGetOpenAppointments_thenUnauthorizedResponse() throws Exception {
        mockMvc.perform(get(ENDPOINT_OPEN_APPOINTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @ValueSource(strings = {
            // expired
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGkiLCJyb2xlcyI6IkFETUlOIFJFQURfUFJJVklMRUdFIFVTRVIgV1JJVEVfUFJJVklMRUdFIiwiaXNzIjoiYmx1YmFuay5jb20iLCJleHAiOjE2NjYyMTQ5MDIsImlhdCI6MTY2NjIxMzcwMn0.rvXQNnAY8DzuTn2HTZPdlLqPiDQpVA4sIO48PcgxeBg",
            // Corrupted signature
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGkiLCJyb2xlcyI6IkFETUlOIFJFQURfUFJJVklMRUdFIFVTRVIgV1JJVEVfUFJJVklMRUdFIiwiaXNzIjoiYmx1YmFuay5jb20iLCJleHAiOjE2NjYyMTQ5MDIsImlhdCI6MTY2NjIxMzcwMn0.rvXQNnAY8DzuTn2HTZPdlLqPiDQpVA4sIO48PcgxeBg_",
            // Not start with Bearer
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGkiLCJyb2xlcyI6IkFETUlOIFJFQURfUFJJVklMRUdFIFVTRVIgV1JJVEVfUFJJVklMRUdFIiwiaXNzIjoiYmx1YmFuay5jb20iLCJleHAiOjE2NjYyMTQ5MDIsImlhdCI6MTY2NjIxMzcwMn0.rvXQNnAY8DzuTn2HTZPdlLqPiDQpVA4sIO48PcgxeBg",
            // Not parsable payload
            "Bearer eyJhbGciOiJIUzI1NiJ9.iNvaLidPaYloAd.rvXQNnAY8DzuTn2HTZPdlLqPiDQpVA4sIO48PcgxeBg",
    })
    @ParameterizedTest
    @Sql("/sql-data/add-patient-users.sql")
    void givenInvalidToken_whenGetOpenAppointments_thenUnauthorizedResponse(String invalidToken) throws Exception {
        mockMvc.perform(get(ENDPOINT_OPEN_APPOINTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, invalidToken)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("/sql-data/add-patient-and-appointments.sql")
    void givenPatientRole_whenTakeOpenAppointment_thenItIsTakenByPatient() throws Exception {
        final String token = jwtTokenUtil.generateAccessToken(userService.loadUserByUsername(USER_WITH_PATIENT_ROLE));

        mockMvc.perform(put(ENDPOINT_OPEN_APPOINTMENTS_APPOINTMENT_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(PATIENT_CONTACT.jsonString())
                ).andDo(print())
                .andExpect(status().isOk());
    }
}