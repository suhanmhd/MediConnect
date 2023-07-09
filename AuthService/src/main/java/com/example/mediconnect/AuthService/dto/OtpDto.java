package com.example.mediconnect.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpDto {

    private UUID id;
    private String email;
    private  String status;
    private String otp;
}
