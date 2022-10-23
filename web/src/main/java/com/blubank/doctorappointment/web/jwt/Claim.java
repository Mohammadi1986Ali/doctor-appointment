package com.blubank.doctorappointment.web.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Claim {
    String username;
    List<String> roles;
}
