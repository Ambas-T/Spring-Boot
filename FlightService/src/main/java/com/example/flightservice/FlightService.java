package com.example.flightservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Transactional
    public FlightBooking bookFlight(FlightBooking booking) {
        return flightBookingRepository.save(booking);
    }

    public FlightBooking getBookingById(Long id) {
        return flightBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight booking not found"));
    }

    public List<FlightBooking> getAllBookings() {
        return flightBookingRepository.findAll();
    }

    @Transactional
    public void cancelBooking(Long id) {
        flightBookingRepository.deleteById(id);
    }
}

