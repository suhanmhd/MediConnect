package com.example.mediconnect.UserService.entity.doctor;



import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "id", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String university;
    private String degree;
    private int startYear;
    private int endYear;

    // Add a reference to the DoctorCredentials entity
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorCredentials doctor;
}
