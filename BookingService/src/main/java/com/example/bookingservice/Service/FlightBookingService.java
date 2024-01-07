package com.example.bookingservice.Service;

import com.example.bookingservice.Client.FlightClient;
import com.example.bookingservice.DTO.FlightBookingRequest;
import com.example.bookingservice.DTO.FlightBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightBookingService {

    @Autowired
    private FlightClient flightClient; // Feign client

    public Long bookFlight(Long id, String flightDetails) {
        try {
            FlightBookingResponse response = flightClient.bookFlight(new FlightBookingRequest(id, flightDetails));
            if (response.isSuccess()) {
                return response.getBookingId();
            } else {
                throw new RuntimeException("Flight booking failed");
            }
        } catch (Exception e) {
            // Log and handle the exception as per your requirements
            throw new RuntimeException("Error booking flight: " + e.getMessage());
        }
    }

    public void compensateFlightBooking(Long flightBookingId) {
        try {
            flightClient.cancelFlightBooking(flightBookingId);
        } catch (Exception e) {
            // Handle cancellation failure
            throw new RuntimeException("Failed to compensate flight booking: " + e.getMessage());
        }
    }

}

