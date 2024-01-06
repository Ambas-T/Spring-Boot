package com.example.bookservice.Service;

import com.example.bookservice.Client.CarServiceClient;
import com.example.bookservice.Client.FlightServiceClient;
import com.example.bookservice.Client.HotelServiceClient;
import com.example.bookservice.Constants.BookingStatus;
import com.example.bookservice.DTO.BookingDto;
import com.example.bookservice.DTO.CarBookingResponse;
import com.example.bookservice.DTO.FlightBookingResponse;
import com.example.bookservice.DTO.HotelBookingResponse;
import com.example.bookservice.Entity.Booking;
import com.example.bookservice.Exception.BookingException;
import com.example.bookservice.Repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    // Autowire other services (CarServiceClient, HotelServiceClient, FlightServiceClient)

    // Assuming CarServiceClient, HotelServiceClient, FlightServiceClient are available
    @Autowired
    private CarServiceClient carServiceClient;

    @Autowired
    private HotelServiceClient hotelServiceClient;

    @Autowired
    private FlightServiceClient flightServiceClient;

    @Transactional
    public Booking createBooking(BookingDto bookingDto) throws BookingException {
        Booking booking = new Booking();
        try {
            // Step 1: Initialize and save the initial booking state
            booking.fromDto(bookingDto); // Assume a method to map DTO to entity
            booking = bookingRepository.save(booking);

            // Step 2: Initiate Car Booking
            CarBookingResponse carBookingResponse = carServiceClient.initiateCarBooking(booking.getId(), bookingDto.getCarDetails());
            if (!carBookingResponse.isSuccess()) {
                throw new BookingException("Car Booking Failed");
            }
            booking.setCarBookingId(carBookingResponse.getBookingId());

            // Step 3: Initiate Hotel Booking
            HotelBookingResponse hotelBookingResponse = hotelServiceClient.initiateHotelBooking(booking.getId(), bookingDto.getHotelDetails());
            if (!hotelBookingResponse.isSuccess()) {
                compensateCarBooking(booking.getCarBookingId());
                throw new BookingException("Hotel Booking Failed");
            }
            booking.setHotelBookingId(hotelBookingResponse.getBookingId());

            // Step 4: Initiate Flight Booking
            FlightBookingResponse flightBookingResponse = flightServiceClient.initiateFlightBooking(booking.getId(), bookingDto.getFlightDetails());
            if (!flightBookingResponse.isSuccess()) {
                compensateCarBooking(booking.getCarBookingId());
                compensateHotelBooking(booking.getHotelBookingId());
                throw new BookingException("Flight Booking Failed");
            }
            booking.setFlightBookingId(flightBookingResponse.getBookingId());

            // Step 5: Finalize Booking
            booking.setStatus(BookingStatus.CONFIRMED);
            booking = bookingRepository.save(booking);
            return booking;

        } catch (Exception ex) {
            // Step 6: Handle any exceptions and perform necessary compensations
            compensateBooking(booking);
            throw new BookingException("Flight Booking Failed");
        }
    }

    private void compensateBooking(Booking booking) {
        if (booking.getCarBookingId() != null) {
            compensateCarBooking(booking.getCarBookingId());
        }
        if (booking.getHotelBookingId() != null) {
            compensateHotelBooking(booking.getHotelBookingId());
        }
        if (booking.getFlightBookingId() != null) {
            compensateFlightBooking(booking.getFlightBookingId());
        }
    }

//    public void handleCarBookingResponse(CarBookingResponseEvent event) {
//        // Logic to handle car booking response
//        // Update booking entity status
//        // ...
//    }
//
//    public void handleHotelBookingResponse(HotelBookingResponseEvent event) {
//        // Similar to car booking response
//    }
//
//    public void handleFlightBookingResponse(FlightBookingResponseEvent event) {
//        // Similar to car booking response
//    }

    // Additional methods for compensating transactions in case of failures
    public void compensateCarBooking(Long bookingId) {
        // Logic to cancel car booking
    }

    public void compensateHotelBooking(Long bookingId) {
        // Logic to cancel hotel booking
    }

    public void compensateFlightBooking(Long bookingId) {
        // Logic to cancel flight booking
    }
}

