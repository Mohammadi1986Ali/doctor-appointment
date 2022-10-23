package com.blubank.doctorappointment.dto.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Principal extends User {
    public Principal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Principal(UserDetails userDetails) {
        this(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
