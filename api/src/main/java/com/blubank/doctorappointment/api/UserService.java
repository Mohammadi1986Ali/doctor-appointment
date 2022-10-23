package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.dto.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User save(User user);

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
