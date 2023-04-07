package com.example.demo.dto;

import com.example.demo.model.FonoAPIStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class PhoneDetailsDTO {
    private FonoAPIInfo fonoInfo;
    private BookingInfo bookingInfo;
    @Data
    @NoArgsConstructor
    public static class FonoAPIInfo{
        private FonoAPIStatus status;
        private String technology;
        private String _2GBands;
        private String _3GBands;
        private String _4GBands;
        public FonoAPIInfo(FonoAPIStatus status){
            this.status = status;
        }
    }
    @Data
    public static class BookingInfo{
        private Long bookingId;
        private String bookedBy;
        private LocalDateTime bookedAt;
    }
    public PhoneDetailsDTO(FonoAPIInfo fonoInfo, BookingInfo bookingInfo){
        this.fonoInfo =fonoInfo;
        this.bookingInfo = bookingInfo;

    }
}
