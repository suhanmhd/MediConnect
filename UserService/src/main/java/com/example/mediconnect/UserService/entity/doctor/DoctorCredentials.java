package com.example.mediconnect.UserService.entity.doctor;

import com.example.mediconnect.UserService.entity.AvailableSlot;
import com.example.mediconnect.UserService.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCredentials extends BaseEntity {
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
    @Lob
    private String about;

    private String location;

    private long feesPerConsultation;

    private String image;

    private  String license;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "doctor")
    @ToString.Exclude
    private List<AvailableSlot> availableSlots;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "doctor")
    @ToString.Exclude
    private List<JobHistory> jobHistoryList;

    @OneToMany( mappedBy = "doctor")
    @ToString.Exclude
    private List<Education> educationList;

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private ClinicInfo clinicInfo;


}
