package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPhone_IdAndAndReturnedAtIsNull(Long phoneId);

    Long countByReturnedAtIsNullAndPhone_Id(Long phoneId);
}
