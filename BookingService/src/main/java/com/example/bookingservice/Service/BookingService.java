package com.example.bookingservice.Service;

import com.example.bookingservice.DTO.BookingDto;
import com.example.bookingservice.Entity.Booking;
import com.example.bookingservice.Exception.BookingException;
import com.example.bookingservice.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarBookingService carBookingService;
    private final HotelBookingService hotelBookingService;
    private final FlightBookingService flightBookingService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CarBookingService carBookingService, HotelBookingService hotelBookingService, FlightBookingService flightBookingService) {
        this.bookingRepository = bookingRepository;
        this.carBookingService = carBookingService;
        this.hotelBookingService = hotelBookingService;
        this.flightBookingService = flightBookingService;
    }

    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) throws BookingException {
        // Start the Saga
        Booking booking = new Booking();
        booking.setStatus("PENDING");
        booking.setSagaState("STARTED");
        booking = bookingRepository.save(booking);

        try {
            Long carBookingId = carBookingService.bookCar(booking.getId(), bookingDto.getCarDetails());
            booking.setCarBookingId(carBookingId);

            Long hotelBookingId = hotelBookingService.bookHotel(booking.getId(), bookingDto.getHotelDetails());
            booking.setHotelBookingId(hotelBookingId);

            Long flightBookingId = flightBookingService.bookFlight(booking.getId(), bookingDto.getFlightDetails());
            booking.setFlightBookingId(flightBookingId);

            booking.setStatus("CONFIRMED");
            booking.setSagaState("COMPLETED");
        } catch (Exception e) {
            // Compensating Transactions
            compensateBookings(booking);
            throw new BookingException("Error"); // or handle more gracefully
        }

        return convertToDto(bookingRepository.save(booking));
    }

    private void compensateBookings(Booking booking) {
        if (booking.getCarBookingId() != null) {
            carBookingService.compensateCarBooking(booking.getCarBookingId());
        }
        if (booking.getHotelBookingId() != null) {
            hotelBookingService.compensateHotelBooking(booking.getHotelBookingId());
        }
        if (booking.getFlightBookingId() != null) {
            flightBookingService.compensateFlightBooking(booking.getFlightBookingId());
        }
        booking.setStatus("CANCELLED");
        booking.setSagaState("COMPENSATED");
    }

    // Additional methods like findAllBookings, findBookingById, updateBooking, deleteBooking...
    // Conversion method from Booking entity to BookingDto
    private BookingDto convertToDto(Booking booking) {
        // Conversion logic
        return null;
    }
}
