package com.example.hotelservice;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelReservationRepository extends MongoRepository<HotelReservation, String> {
    // Custom query methods if needed
}

