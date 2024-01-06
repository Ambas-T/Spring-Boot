package com.example.carservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {
    // Custom queries if needed
}

