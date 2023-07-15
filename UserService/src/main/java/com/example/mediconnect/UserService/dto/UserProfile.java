package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;



    private  String gender;


    private  int age;

    private String image;


}
