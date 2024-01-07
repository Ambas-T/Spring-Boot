package com.example.bookingservice.Controller;

import com.example.bookingservice.DTO.BookingDto;
import com.example.bookingservice.Exception.BookingException;
import com.example.bookingservice.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

//    @GetMapping
//    public ResponseEntity<List<BookingDto>> getAllBookings() {
//        List<BookingDto> bookings = bookingService.findAllBookings();
//        return ResponseEntity.ok(bookings);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
//        BookingDto booking = bookingService.findBookingById(id);
//        return ResponseEntity.ok(booking);
//    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) throws BookingException {
        BookingDto newBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok(newBooking);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
//        BookingDto updatedBooking = bookingService.updateBooking(id, bookingDto);
//        return ResponseEntity.ok(updatedBooking);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
//        bookingService.deleteBooking(id);
//        return ResponseEntity.noContent().build();
//    }
}
