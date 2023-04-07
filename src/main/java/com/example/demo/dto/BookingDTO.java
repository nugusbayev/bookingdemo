package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private LocalDateTime bookedAt;
    private LocalDateTime returnedAt;
    private Long phoneId;
    private String phoneSerialNumber;
    private String phoneModel;
}
