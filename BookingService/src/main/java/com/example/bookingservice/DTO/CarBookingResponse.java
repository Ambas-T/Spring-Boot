package com.example.bookingservice.DTO;

import lombok.Data;

@Data
public class CarBookingResponse {
    private boolean success;
    private Long bookingId;
}
