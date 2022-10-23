package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.api.UserService;
import com.blubank.doctorappointment.dto.security.Principal;
import com.blubank.doctorappointment.dto.security.Role;
import com.blubank.doctorappointment.dto.security.User;
import com.blubank.doctorappointment.entity.SecurityRole;
import com.blubank.doctorappointment.entity.SecurityUser;
import com.blubank.doctorappointment.exceptions.UserAlreadyExistsException;
import com.blubank.doctorappointment.repository.UserRepository;
import com.blubank.doctorappointment.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = repository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(String.format("User %s not found", username));

        return new Principal(user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<SecurityRole> roles) {
        Set<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(role -> {
                    Assert.isTrue(!role.getRoleName().startsWith("ROLE_"), () -> role + " cannot start with ROLE_ (it is automatically added)");
                    return new SimpleGrantedAuthority("ROLE_" + role.getRoleName());
                }).collect(Collectors.toSet());
        return grantedAuthorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities1(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }

    @Override
    @Transactional
    public User save(User user) {
        SecurityUser userEntity = mapper.map(user);

        final String username = Optional.ofNullable(user.getUsername())
                .map(String::toLowerCase).orElseThrow(IllegalArgumentException::new);

        if (username.isBlank())
            throw new IllegalArgumentException("Username can not be empty");

        user.setUsername(username);

        if (repository.existsByUsername(userEntity.getUsername()))
            throw new UserAlreadyExistsException(String.format("User %s is already exists!", user.getUsername()));

        return mapper.map(repository.save(userEntity));
    }
}
