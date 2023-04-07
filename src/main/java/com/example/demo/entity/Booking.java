package com.example.demo.entity;

import com.example.demo.dto.PhoneDetailsDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_id_seq")
    private long id;

    @Column(name = "BOOKED_BY", nullable = false)
    @NotNull
    private String bookedBy;

    @Column(name = "BOOKED_AT", nullable = false)
    @NotNull
    private LocalDateTime bookedAt;

    @Column(name = "RETURNED_AT")
    private LocalDateTime returnedAt;

    @JoinColumn(name = "PHONE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Phone phone;
    public Booking(String bookedBy, LocalDateTime bookedAt, Phone phone){
        this.bookedAt = bookedAt;
        this.bookedBy = bookedBy;
        this.phone = phone;
    }
    public PhoneDetailsDTO.BookingInfo toBookingInfo(){
        PhoneDetailsDTO.BookingInfo bookingInfo = new PhoneDetailsDTO.BookingInfo();
        bookingInfo.setBookedAt(this.getBookedAt());
        bookingInfo.setBookedBy(this.getBookedBy());
        bookingInfo.setBookingId(this.getId());
        return bookingInfo;
    }
}
