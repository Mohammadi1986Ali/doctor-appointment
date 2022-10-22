package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.AppointmentService;
import com.blubank.doctorappointment.web.mapper.AppointmentModelMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
@Import({AppointmentModelMapperImpl.class})
public class AppointmentsResourceWebMvcTest {

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenSaveAppointment_thenItShouldReturn200AsResponse() throws Exception {

        Mockito.when(appointmentService.save(any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAppointment("mock-data/appointment-correct-input.json"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenStartDateAfterEndDate_thenItShouldReturn400AsResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAppointment("mock-data/appointment-start-after-end.json"))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
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
