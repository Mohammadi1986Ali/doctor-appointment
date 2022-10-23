package com.blubank.doctorappointment.dto.security;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private UUID userId;
    private String username;
    private String encryptedPassword;
    private Set<Role> roles;
}
