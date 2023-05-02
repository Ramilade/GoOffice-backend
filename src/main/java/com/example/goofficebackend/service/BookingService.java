package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    public Booking createBooking(int deskId, int employeeId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        // Retrieve the desk and employee from the database
        Desk desk = deskRepository.findById(deskId).orElse(null);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (desk == null || employee == null) {
            throw new IllegalArgumentException("Invalid desk or employee ID");
        }

        // Create a new booking object
        Booking booking = new Booking();
        booking.setStartDate(startDate);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setEndDate(endDate);
        booking.setDesk(desk);
        booking.setEmployee(employee);

        // Save the booking in the database
        return bookingRepository.save(booking);
    }

}
