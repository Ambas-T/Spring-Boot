package com.example.bookingservice.Client;

import com.example.bookingservice.DTO.FlightBookingRequest;
import com.example.bookingservice.DTO.FlightBookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "flightService", url = "http://localhost:8083") // Adjust the URL
public interface FlightClient {

    @PostMapping("/api/flights/book")
    FlightBookingResponse bookFlight(@RequestBody FlightBookingRequest request);

    @PostMapping("/flight/cancel/{flightBookingId}")
    void cancelFlightBooking(@PathVariable("flightBookingId") Long flightBookingId);
}
