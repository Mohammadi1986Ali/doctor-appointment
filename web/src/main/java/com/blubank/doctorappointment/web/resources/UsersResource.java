package com.blubank.doctorappointment.web.resources;

import com.blubank.doctorappointment.api.UserService;
import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.generated.v1.api.UsersApi;
import com.blubank.doctorappointment.generated.v1.model.CreateUserRequest;
import com.blubank.doctorappointment.generated.v1.model.CreateUserResponse;
import com.blubank.doctorappointment.web.mapper.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class UsersResource implements UsersApi {

    private final UserService service;
    private final UserModelMapper mapper;

    @Override
    public ResponseEntity<CreateUserResponse> addNewUser(CreateUserRequest createUserRequest) {
        User user = mapper.map(createUserRequest);
        CreateUserResponse response = mapper.map(service.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
