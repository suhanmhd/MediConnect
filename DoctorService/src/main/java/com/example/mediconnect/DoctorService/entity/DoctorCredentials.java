package com.example.mediconnect.DoctorService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCredentials extends BaseEntity{


     @Id
     @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "text")
    private List<String> timings;

    private String name;

    private String password;

    private boolean enabled;

    private String isApproved;

//    private String timings;




    private String specialization;

    private String experience;

    private long feesPerConsultation;

    private  String license;
}
