package com.example.bookservice.DTO;

import lombok.Data;

@Data
public class HotelBookingResponse {
    private boolean success;
    private Long bookingId;
}
