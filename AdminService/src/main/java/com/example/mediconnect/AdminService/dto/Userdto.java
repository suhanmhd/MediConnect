package com.example.mediconnect.AdminService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Userdto {
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;

    private String password;

    private  String gender;


    private  int age;

    private boolean enabled;

}
