package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Data
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "phone_id_seq")
    private long id;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "device", nullable = false)
    private String device;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "available")
    private boolean available;
}
