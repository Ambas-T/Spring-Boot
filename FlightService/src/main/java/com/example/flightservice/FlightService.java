package com.example.flightservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Transactional
    public FlightBooking bookFlight(FlightBookignRequestDto booking) {
        FlightBooking entity = new FlightBooking();
        mapDtoToEntity(booking, entity);
        return flightBookingRepository.save(entity);
    }

    private void mapDtoToEntity(FlightBookignRequestDto dto, FlightBooking entity) {
        entity.setId(dto.getUserId());
        entity.setFlightNumber(dto.getCarType());
        entity.setDepartureTime(dto.getStartDate());
        entity.setArrivalTime(dto.getEndDate());
    }

    public FlightBooking getBookingById(Long id) {
        return flightBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight booking not found"));
    }

    public List<FlightBooking> getAllBookings() {
        return flightBookingRepository.findAll();
    }

    @Transactional
    public void cancelBooking(Long id) {
        flightBookingRepository.deleteById(id);
    }
}

