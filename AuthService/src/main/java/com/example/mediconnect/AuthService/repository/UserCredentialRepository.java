package com.example.mediconnect.AuthService.repository;


import com.example.mediconnect.AuthService.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {
    Optional<UserCredential> findByName(String username);
    boolean existsByEmail(String email);
    boolean existsByName(String username);


    Optional<UserCredential> findByEmail(String email);
}
