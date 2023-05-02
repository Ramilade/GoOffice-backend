package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public List<BookingResponse> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(b -> new BookingResponse(b)).toList();

    }

}
