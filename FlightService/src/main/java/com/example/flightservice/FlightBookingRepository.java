package com.example.flightservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    // Custom query methods if needed
}

