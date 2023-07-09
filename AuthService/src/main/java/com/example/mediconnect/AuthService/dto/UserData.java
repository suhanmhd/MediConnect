package com.example.mediconnect.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {


    private UUID id;
    private String firstname;


    private String lastname;


    private String name;

    private String email;

    private  String gender;


    private  int age;

    private String password;

    private boolean enabled;

}
