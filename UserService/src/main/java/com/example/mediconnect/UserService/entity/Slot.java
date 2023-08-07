package com.example.mediconnect.UserService.entity;

import com.example.mediconnect.UserService.entity.AvailableSlot;
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
public class Slot {

        @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "id", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "available_slot_id")
    private AvailableSlot availableSlot;

    private String startTime;
    private String endTime;
    private boolean status;

    // Other properties, getters, and setters
}