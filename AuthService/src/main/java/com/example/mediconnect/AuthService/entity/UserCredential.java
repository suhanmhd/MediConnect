package com.example.mediconnect.AuthService.entity;

import com.example.mediconnect.AuthService.dto.Role;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AuthUser")
public class UserCredential extends BaseEntity {


    @Column(name = "username")
    private String name;


    private String email;



    private String password;

    private boolean enabled;
    private String isApproved;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String otp;
    private LocalDateTime otpGeneratedTime;




}

