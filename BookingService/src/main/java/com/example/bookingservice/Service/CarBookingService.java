package com.example.bookingservice.Service;

import com.example.bookingservice.Client.CarClient;
import com.example.bookingservice.DTO.CarBookingRequest;
import com.example.bookingservice.DTO.CarBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CarBookingService {

    @Autowired
    private CarClient carClient;

    public Long bookCar(Long bookingId, String carDetails) {
        LocalDate today = LocalDate.now();

        // Create a CarBookingRequest with today's date for both start and end dates
        CarBookingRequest request = new CarBookingRequest(bookingId, carDetails, today, today);
        CarBookingResponse response = carClient.bookCar(request);

        // Handle the response
        if (response.isSuccess()) {
            return response.getBookingId();
        } else {
            throw new RuntimeException("Car booking failed"); // Or handle it more gracefully
        }
    }

    public void compensateCarBooking(Long carBookingId) {
        try {
            carClient.cancelCarBooking(carBookingId);
        } catch (Exception e) {
            // Log the exception and handle it as per your application's requirements
            // You might also want to consider what to do if the cancellation fails
            throw new RuntimeException("Failed to compensate car booking: " + e.getMessage());
        }
    }
}
