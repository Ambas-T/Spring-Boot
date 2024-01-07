package com.example.hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotel-reservations")
public class HotelReservationController {

    @Autowired
    private HotelService hotelService;

    // Create a hotel reservation
    @PostMapping
    public ResponseEntity<HotelReservationDto> createReservation(@Valid @RequestBody HotelReservationDto reservationDto) {
        HotelReservation reservation = hotelService.createReservation(reservationDto);
        HotelReservationDto responseDto = mapToDto(reservation);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get a single reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<HotelReservationDto> getReservationById(@PathVariable Long id) {
        HotelReservation reservation = hotelService.getReservationById(id);
        HotelReservationDto responseDto = mapToDto(reservation);
        return ResponseEntity.ok(responseDto);
    }

    // Get all reservations
    @GetMapping
    public ResponseEntity<List<HotelReservationDto>> getAllReservations() {
        List<HotelReservation> reservations = hotelService.getAllReservations();
        List<HotelReservationDto> dtoList = reservations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Update a reservation
    @PutMapping("/{id}")
    public ResponseEntity<HotelReservationDto> updateReservation(@PathVariable Long id, @Valid @RequestBody HotelReservationDto reservationDto) {
        HotelReservation updatedReservation = hotelService.updateReservation(id, reservationDto);
        HotelReservationDto responseDto = mapToDto(updatedReservation);
        return ResponseEntity.ok(responseDto);
    }

    // Delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        hotelService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private HotelReservationDto mapToDto(HotelReservation reservation) {
        HotelReservationDto dto = new HotelReservationDto();
        dto.setUserId(reservation.getUserId());
        dto.setHotelName(reservation.getHotelName());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        return dto;
    }
}

