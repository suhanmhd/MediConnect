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



    private String name;

    private String password;

    private boolean enabled;

    private String isApproved;
    private String specialization;

    private String experience;

    private long feesPerConsultation;

    private String image;

    private  String license;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "doctor")
    private List<AvailableSlot> availableSlots;


}
