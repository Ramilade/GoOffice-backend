package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingRequest;
import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DepartmentRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookingService {


    private BookingRepository bookingRepository;
    private DeskRepository deskRepository;
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    public BookingService(BookingRepository bookingRepository, DeskRepository deskRepository, EmployeeRepository employeeRepository) {
        this.bookingRepository = bookingRepository;
        this.deskRepository = deskRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<BookingResponse> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(b -> new BookingResponse(b)).toList();

    }

    public Booking createBooking(BookingRequest b) {

        Employee employee = employeeRepository.findById(b.getEmployeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No employee with this ID"));

        // Create a new booking object
        Booking booking = new Booking();
        booking.setShiftStart(b.getShiftStart());
        booking.setShiftEnd(b.getShiftEnd());
        booking.setEmployee(employee);

        // Save the booking in the database
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(int id, BookingRequest bookingRequest) {

        // Find the booking in the database
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No booking with this ID"));

        // Update the booking object
        booking.setShiftStart(bookingRequest.getShiftStart());
        booking.setShiftEnd(bookingRequest.getShiftEnd());

        // Save the booking in the database
        return bookingRepository.save(booking);
    }

    public void deleteBooking(int id) {
        // Find the booking in the database
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No booking with this ID"));

        // Delete the booking from the database
        bookingRepository.delete(booking);
    }
}
