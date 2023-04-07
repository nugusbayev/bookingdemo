package com.example.demo.repository;

import com.example.demo.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByAvailable(boolean available);
    @Modifying(flushAutomatically = true)
    @Query("UPDATE Phone p SET p.available = FALSE WHERE p.available = TRUE AND p.id = :phoneId")
    Integer updateAvailability(@Param("phoneId") Long phoneId);
}
