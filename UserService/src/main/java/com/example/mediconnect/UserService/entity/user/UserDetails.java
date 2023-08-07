package com.example.mediconnect.UserService.entity.user;

import com.example.mediconnect.UserService.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails extends BaseEntity {


     @Id
     @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;

    private String password;

    private  String gender;


    private  int age;

    private boolean enabled;

    private String image;



}
