package com.blubank.doctorappointment.web.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.port=0"
)
@AutoConfigureMockMvc
public class AppointmentsResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenPeriodIsLessThanThirtyMinutes_thenNoAppointmentShouldBeAdded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAppointment("mock-data/appointment-period-less-than-thirty-minutes.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("[]"));
    }

    private String createAppointment(String fileAddress) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream(fileAddress)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

}
