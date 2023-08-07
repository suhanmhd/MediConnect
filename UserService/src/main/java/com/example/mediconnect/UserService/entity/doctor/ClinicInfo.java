package com.example.mediconnect.UserService.entity.doctor;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClinicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String clinicName;
    private String clinicAddress;

    @ElementCollection
    private List<String> clinicImages;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorCredentials doctor;
}
