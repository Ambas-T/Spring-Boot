package com.example.hotelservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelReservationDto {
    private Long userId;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
