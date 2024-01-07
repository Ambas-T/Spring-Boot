package com.example.bookingservice.Service;

import com.example.bookingservice.Client.CarClient;
import com.example.bookingservice.DTO.CarBookingRequest;
import com.example.bookingservice.DTO.CarBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarBookingService {
    // Assuming you have a repository or a client to interact with the CarService
    @Autowired
    private CarClient carClient; // This is a Feign client to be defined next

    public Long bookCar(Long bookingId, String carDetails) {
        // Convert carDetails string to the required format or object
        // Call the CarService to perform the booking
        CarBookingResponse response = carClient.bookCar(new CarBookingRequest(bookingId, carDetails));

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
