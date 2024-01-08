package com.example.hotelservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Transactional
    public HotelReservation createReservation(HotelReservationDto reservationDto) {
        HotelReservation reservation = new HotelReservation();
        mapDtoToEntity(reservationDto, reservation);
        return hotelReservationRepository.save(reservation);
    }

    public HotelReservation getReservationById(Long id) {
        return hotelReservationRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
    }

    public List<HotelReservation> getAllReservations() {
        return hotelReservationRepository.findAll();
    }

    @Transactional
    public HotelReservation updateReservation(Long id, HotelReservationDto reservationDto) {
        HotelReservation existingReservation = getReservationById(id);
        mapDtoToEntity(reservationDto, existingReservation);
        return hotelReservationRepository.save(existingReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        hotelReservationRepository.deleteById(String.valueOf(id));
    }

    private void mapDtoToEntity(HotelReservationDto dto, HotelReservation entity) {
        // Implement the mapping logic from DTO to entity
        LocalDate today = LocalDate.now();
        entity.setUserId(dto.getBookingId());
        entity.setHotelName("Sharaton");
        entity.setCheckInDate(today);
        entity.setCheckOutDate(today);
        dto.setSuccess(true);
        dto.setBookingId(entity.getUserId());
    }
}
