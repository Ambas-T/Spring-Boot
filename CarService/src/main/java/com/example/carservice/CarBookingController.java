package com.example.carservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car-bookings")
public class CarBookingController {

    @Autowired
    private CarService carService;

    // Create a car booking
    @PostMapping
    public ResponseEntity<CarBookingDto> createCarBooking(@Valid @RequestBody CarBookingRequestDto carBookingDto) {
        CarBooking carBooking = carService.createCarBooking(carBookingDto);
        CarBookingDto responseDto = mapToDto(carBooking); // Convert entity to DTO
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Retrieve a single car booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<CarBookingDto> getCarBookingById(@PathVariable Long id) {
        CarBooking carBooking = carService.getCarBookingById(id);
        CarBookingDto responseDto = mapToDto(carBooking); // Convert entity to DTO
        return ResponseEntity.ok(responseDto);
    }

    // Retrieve all car bookings
    @GetMapping
    public ResponseEntity<List<CarBookingDto>> getAllCarBookings() {
        List<CarBooking> carBookings = carService.getAllCarBookings();
        List<CarBookingDto> responseDtos = carBookings.stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    // Update a car booking
    @PutMapping("/{id}")
    public ResponseEntity<CarBookingDto> updateCarBooking(@PathVariable Long id, @Valid @RequestBody CarBookingRequestDto carBookingDto) {
        CarBooking updatedCarBooking = carService.updateCarBooking(id, carBookingDto);
        CarBookingDto responseDto = mapToDto(updatedCarBooking); // Convert entity to DTO
        return ResponseEntity.ok(responseDto);
    }

    // Delete a car booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarBooking(@PathVariable Long id) {
        carService.deleteCarBooking(id);
        return ResponseEntity.noContent().build();
    }

    private CarBookingDto mapToDto(CarBooking carBooking) {
        CarBookingDto response = new CarBookingDto();
        response.setBookingId(carBooking.getId());
        response.setSuccess(true);
        return response;
    }
}
