package com.example.bookingservice.Client;

import com.example.bookingservice.DTO.CarBookingRequest;
import com.example.bookingservice.DTO.CarBookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "carService", url = "http://localhost:8080") // Replace with actual service URL
public interface CarClient {

    @PostMapping("/car-bookings")
    CarBookingResponse bookCar(@RequestBody CarBookingRequest request);

    @PostMapping("/car/cancel/{carBookingId}")
    void cancelCarBooking(@PathVariable("carBookingId") Long carBookingId);
}
