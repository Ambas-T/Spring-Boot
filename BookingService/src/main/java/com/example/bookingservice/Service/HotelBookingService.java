package com.example.bookingservice.Service;

import com.example.bookingservice.Client.HotelClient;
import com.example.bookingservice.DTO.HotelBookingRequest;
import com.example.bookingservice.DTO.HotelBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotelBookingService {

    @Autowired
    private HotelClient hotelClient; // Feign client

    public Long bookHotel(Long id, String hotelDetails) {
        try {
            LocalDate today = LocalDate.now();
            HotelBookingResponse response = hotelClient.bookHotel(new HotelBookingRequest(id, hotelDetails, today, today));
            if (response.isSuccess()) {
                return response.getBookingId();
            } else {
                throw new RuntimeException("Hotel booking failed");
            }
        } catch (Exception e) {
            // Log and handle the exception as per your requirements
            throw new RuntimeException("Error booking hotel: " + e.getMessage());
        }
    }

    public void compensateHotelBooking(Long hotelBookingId) {
        try {
            hotelClient.cancelHotelBooking(hotelBookingId);
        } catch (Exception e) {
            // Handle cancellation failure
            throw new RuntimeException("Failed to compensate hotel booking: " + e.getMessage());
        }
    }

}
