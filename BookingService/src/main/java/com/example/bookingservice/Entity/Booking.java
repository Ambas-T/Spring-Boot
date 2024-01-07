package com.example.bookingservice.Entity;


import com.example.bookingservice.DTO.BookingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long carBookingId;
    private Long hotelBookingId;
    private Long flightBookingId;
    private String status;
    private String sagaState;

    public void fromDto(BookingDto bookingDto) {
    }
}
