package com.example.flightservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookignRequestDto {
    private Long userId;
    private String carType;
    private LocalDate startDate;
    private LocalDate endDate;
}
