package com.example.bookingservice.DTO;

import lombok.Data;

@Data
public class HotelBookingResponse {
    private boolean success;
    private Long bookingId;
}
