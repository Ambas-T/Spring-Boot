package com.example.flightservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Create a new flight booking
    @PostMapping("/book")
    public ResponseEntity<FlightBooking> bookFlight(@RequestBody FlightBooking booking) {
        FlightBooking newBooking = flightService.bookFlight(booking);
        return ResponseEntity.ok(newBooking);
    }

    // Get a specific flight booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<FlightBooking> getBookingById(@PathVariable Long id) {
        FlightBooking booking = flightService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    // Get all flight bookings
    @GetMapping
    public ResponseEntity<List<FlightBooking>> getAllBookings() {
        List<FlightBooking> bookings = flightService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Cancel a flight booking
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        flightService.cancelBooking(id);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }
}

