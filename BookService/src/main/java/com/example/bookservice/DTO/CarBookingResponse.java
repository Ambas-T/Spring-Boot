package com.example.bookservice.DTO;

import lombok.Data;

@Data
public class CarBookingResponse {
    private boolean success;
    private Long bookingId;
}
