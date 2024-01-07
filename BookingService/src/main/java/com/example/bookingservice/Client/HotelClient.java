package com.example.bookingservice.Client;

import com.example.bookingservice.DTO.HotelBookingRequest;
import com.example.bookingservice.DTO.HotelBookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotelService", url = "http://localhost:8082") // Adjust the URL
public interface HotelClient {

    @PostMapping("/hotel/book")
    HotelBookingResponse bookHotel(@RequestBody HotelBookingRequest request);

    @PostMapping("/hotel/cancel/{hotelBookingId}")
    void cancelHotelBooking(@PathVariable("hotelBookingId") Long hotelBookingId);
}
