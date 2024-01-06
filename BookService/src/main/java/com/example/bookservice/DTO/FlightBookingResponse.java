package com.example.bookservice.DTO;

import lombok.Data;

@Data
public class FlightBookingResponse {
    private boolean success;
    private Long BookingId;
}
