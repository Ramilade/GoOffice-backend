package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingRequest;
import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    private DeskRepository deskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<BookingResponse> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(b -> new BookingResponse(b)).toList();

    }

    public Booking createBooking(BookingRequest b) {

        Employee employee = employeeRepository.findById(b.getEmployeeId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"No employee with this ID"));

        // Create a new booking object
        Booking booking = new Booking();
        booking.setShiftStart(b.getShiftStart());
        booking.setShiftEnd(b.getShiftEnd());
        booking.setEmployee(employee);

        // Save the booking in the database
        return bookingRepository.save(booking);
    }

}
