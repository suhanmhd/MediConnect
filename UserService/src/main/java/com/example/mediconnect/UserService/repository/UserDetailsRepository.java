package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    Optional<UserDetails> findByName(String name);


}
