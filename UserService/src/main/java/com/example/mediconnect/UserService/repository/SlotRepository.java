package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.entity.AvailableSlot;
import com.example.mediconnect.UserService.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
//    List<Slot> findBySlot(List<AvailableSlot> slot);
}
