package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class SwaggerUITest {
    public static final String ENDPOINT_SWAGGER_UI = "/swagger-ui.html";
    public static final String ENDPOINT_SWAGGER_UI_INDEX = "/swagger-ui/index.html";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    Logger logger;

    @Test
    void shouldHaveAccessToSwaggerUI()throws Exception{
    mockMvc.perform(get(ENDPOINT_SWAGGER_UI_INDEX))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Swagger UI")));
    }
}
