package com.example.flightservice;

import lombok.Data;

@Data
public class FlightBookingRequestDTO {
    private boolean success;
    private Long BookingId;
}
