package com.example.mediconnect.UserService.entity.doctor;

import com.example.mediconnect.UserService.entity.BaseEntity;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "id", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorCredentials doctor;

    private String clinicName;
    private int startYear;
    private String endYear;
    private int duration;

    // Getters and setters, constructors, and other methods
}
