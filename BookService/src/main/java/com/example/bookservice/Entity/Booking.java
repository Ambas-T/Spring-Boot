package com.example.bookservice.Entity;

import com.example.bookservice.DTO.BookingDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void fromDto(BookingDto bookingDto) {
    }
}
