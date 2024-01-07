package com.example.bookingservice.Repository;


import com.example.bookingservice.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
