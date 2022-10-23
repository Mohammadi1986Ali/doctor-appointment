package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<SecurityUser, UUID> {
    boolean existsByUsername(String username);

    SecurityUser findByUsername(String username);
}
