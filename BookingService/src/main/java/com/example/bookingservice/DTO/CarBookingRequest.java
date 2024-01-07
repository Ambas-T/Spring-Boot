package com.example.bookingservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarBookingRequest {
    private Long bookingId;
    private String carDetails;

}