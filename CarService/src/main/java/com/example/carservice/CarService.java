package com.example.carservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarBookingRepository carBookingRepository;

    @Transactional
    public CarBooking createCarBooking(CarBookingRequestDto carBookingDto) {
        CarBooking newCarBooking = new CarBooking();
        mapDtoToEntity(carBookingDto, newCarBooking);
        return carBookingRepository.save(newCarBooking);
    }

    private void mapDtoToEntity(CarBookingRequestDto dto, CarBooking entity) {
        entity.setUserId(dto.getUserId());
        entity.setCarType(dto.getCarType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }

    public CarBooking getCarBookingById(Long id) {
        return carBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car booking not found with id: " + id));
    }

    public List<CarBooking> getAllCarBookings() {
        return  carBookingRepository.findAll();
    }

    public CarBooking updateCarBooking(Long id, CarBookingRequestDto carBookingDto) {
        CarBooking existingCarBooking = carBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car booking not found with id: " + id));

        mapDtoToEntity(carBookingDto, existingCarBooking);
        return carBookingRepository.save(existingCarBooking);
    }

    public void deleteCarBooking(Long id) {
        if (!carBookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Car booking not found with id: " + id);
        }
        carBookingRepository.deleteById(id);
    }
}
