package com.blubank.doctorappointment.utils;

import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.generated.v1.model.BasicAppointmentInfo;
import com.blubank.doctorappointment.generated.v1.model.CreateUserRequest;
import com.blubank.doctorappointment.generated.v1.model.LoginRequest;
import com.blubank.doctorappointment.generated.v1.model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MockDataLoader {

    private static final String MOCK_DATA_CREATE_USER_REQUEST = "mock-data/create-user-request.json";
    private static final String MOCK_DATA_LOGIN_REQUEST = "mock-data/login-request.json";
    private static final String MOCK_DATA_CREATE_APPOINTMENT_REQUEST = "mock-data/create-appointments-request.json";
    private static final String MOCK_DATA_CREATE_APPOINTMENT_RESPONSE = "mock-data/create-appointments-response.json";
    private static final String MOCK_DATA_CREATE_APPOINTMENT_REQUEST_PERIOD_LESS_THAN_30_MINUTES = "mock-data/create-appointments-request-with-less-than-30-minutes.json";
    private static final String MOCK_DATA_CREATE_APPOINTMENT_REQUEST_START_AFTER_END = "mock-data/create-appointment-request-start-after-end.json";
    private static final String MOCK_DATA_USER_WITH_ROLES_AND_PRIVILEGES = "mock-data/user/user-with-roles-and-privileges.json";
    private static final String MOCK_DATA_PATIENT_CONTACT = "mock-data/patient/patient-contact.json";

    private static final JSONParser JSON_PARSER = new JSONParser(JSONParser.MODE_PERMISSIVE);

    private static String readAsString(String path) throws IOException {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (in == null) throw new IllegalArgumentException(path + " not found");
            return IOUtils.toString(in, StandardCharsets.UTF_8.name());
        }
    }

    private static <T> T readAsType(String resourceName, Class<T> type) throws IOException {
        try (InputStream in = MockDataLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new IllegalArgumentException(resourceName + " not found");
            return objectMapper.readValue(in, type);
        }
    }

    private static JSONObject readAsJsonObject(String resourceName) throws IOException, ParseException {
        try (InputStream in = MockDataLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new IllegalArgumentException(resourceName + " not found");
            return (JSONObject) JSON_PARSER.parse(in);
        }
    }

    private static File getResourceAsFile(String resourceName) throws URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) throw new IllegalArgumentException(resourceName + " not found!");
        return new File(url.toURI());
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public interface Resource<T> {

        Class<T> getType();

        String getResourceName();

        default T readApiModel() throws IOException {
            return readAsType(getResourceName(), getType());
        }

        default String jsonString() throws IOException {
            return readAsString(getResourceName());
        }

        default File getResourceFile() throws URISyntaxException {
            return getResourceAsFile(getResourceName());
        }

        default JSONObject jsonObject() throws IOException, ParseException {
            return readAsJsonObject(getResourceName());
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum CreateAppointmentRequest implements Resource<CreateAppointmentRequest> {
        CREATE_APPOINTMENT_REQUEST(MOCK_DATA_CREATE_APPOINTMENT_REQUEST),
        CREATE_APPOINTMENT_REQUEST_PERIOD_LESS_THAN_30_MINUTES(MOCK_DATA_CREATE_APPOINTMENT_REQUEST_PERIOD_LESS_THAN_30_MINUTES),
        CREATE_APPOINTMENT_REQUEST_START_AFTER_END(MOCK_DATA_CREATE_APPOINTMENT_REQUEST_START_AFTER_END);

        private final String resourceName;

        @Override
        public Class<CreateAppointmentRequest> getType() {
            return CreateAppointmentRequest.class;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum BasicAppointmentInfoEnum implements Resource<BasicAppointmentInfo> {
        CREATE_APPOINTMENT_RESPONSE(MOCK_DATA_CREATE_APPOINTMENT_RESPONSE);

        private final String resourceName;

        @Override
        public Class<BasicAppointmentInfo> getType() {
            return BasicAppointmentInfo.class;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum CreateUserRequestEnum implements Resource<CreateUserRequest> {
        CREATE_USER_REQUEST(MOCK_DATA_CREATE_USER_REQUEST);

        private final String resourceName;

        @Override
        public Class<CreateUserRequest> getType() {
            return CreateUserRequest.class;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum LoginRequestEnum implements Resource<LoginRequest> {
        LOGIN_REQUEST(MOCK_DATA_LOGIN_REQUEST);

        private final String resourceName;

        @Override
        public Class<LoginRequest> getType() {
            return LoginRequest.class;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum SecurityUser implements Resource<User> {
        USER_WITH_ROLES_AND_PRIVILEGES(MOCK_DATA_USER_WITH_ROLES_AND_PRIVILEGES);

        private final String resourceName;

        @Override
        public Class<User> getType() {
            return User.class;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum PatientEnum implements Resource<Patient> {
        PATIENT_CONTACT(MOCK_DATA_PATIENT_CONTACT);

        private final String resourceName;

        @Override
        public Class<Patient> getType() {
            return Patient.class;
        }
    }
}
