package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingRequest;
import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DepartmentRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


  public ResponseEntity<List<BookingResponse>> getBookings() {
    List<Booking> bookings = bookingRepository.findAll();
    List<BookingResponse> bookingResponses = bookings.stream()
        .map(BookingResponse::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(bookingResponses);
  }

  public void validateDate(LocalDateTime shiftStart, LocalDateTime shiftEnd) {
    // check that the date is the same
    if (!shiftStart.toLocalDate().isEqual(shiftEnd.toLocalDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end date is not the same");
    }
    // check if startdate or enddate is not before present
    LocalDate shiftStartDate = shiftStart.toLocalDate();
    if (shiftStartDate.isBefore(LocalDate.now()) || shiftEnd.isBefore(LocalDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is before present");
    }
    // check if enddate is more than 4 weeks in the future
    if (shiftEnd.isAfter(LocalDateTime.now().plusWeeks(4))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is more than 4 weeks in the future");
    }
  }

  public ResponseEntity<BookingResponse> createBooking(BookingRequest br) {

    Employee employee = employeeRepository.findById(br.getEmployeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No employee with this ID"));

    if (checkDoubleBooking(br.getShiftStart(), br.getEmployeeId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have a booking on that date and time");
    }


    Booking booking = new Booking();

    Desk availableDesk = deskRepository.findAvailableDesk(br.getShiftStart(), br.getShiftEnd())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No available desk during the specified time period"));

    validateDate(br.getShiftStart(), br.getShiftEnd());

    booking.setShiftStart(br.getShiftStart());
    booking.setShiftEnd(br.getShiftEnd());
    booking.setEmployee(employee);
    booking.setDesk(availableDesk);

    bookingRepository.save(booking);
    BookingResponse bookingResponse = new BookingResponse(booking);
    return ResponseEntity.ok().body(bookingResponse);
  }

    public boolean checkDoubleBooking(LocalDateTime shiftStart, int employeeId) {
        List<Booking> bookings = bookingRepository.findBookingsByShiftStartAndEmployee_Id(shiftStart, employeeId);
        return bookings.stream().anyMatch(booking -> booking.getShiftStart().equals(shiftStart));
    }


    public ResponseEntity<BookingResponse> updateBooking(int id, BookingRequest br) {

    Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No booking with this ID"));

    Desk availableDesk = deskRepository.findAvailableDesk(br.getShiftStart(), br.getShiftEnd())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No available desk during the specified time period"));

    validateDate(br.getShiftStart(), br.getShiftEnd());

    booking.setShiftStart(br.getShiftStart());
    booking.setShiftEnd(br.getShiftEnd());
    booking.setDesk(availableDesk);

    bookingRepository.save(booking);
    BookingResponse bookingResponse = new BookingResponse(booking);
    return ResponseEntity.ok().body(bookingResponse);
  }

  public ResponseEntity<String> deleteBooking(int id) {
    Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No booking with this ID"));
    bookingRepository.delete(booking);
    return ResponseEntity.ok().body("Booking deleted");
  }

  public ResponseEntity<List<BookingResponse>> findBookingsByDate(LocalDate date) {
    List<Booking> list = bookingRepository.findBookingsByDate(date);
    List<BookingResponse> bookingResponses = list.stream().map(BookingResponse::new).toList();
    return ResponseEntity.ok().body(bookingResponses);
  }

  public ResponseEntity<List<Integer>> findAvailableDesksByDate(LocalDate date) {
    LocalDateTime startOfMorning = LocalDateTime.of(date, LocalTime.of(8, 0));
    LocalDateTime endOfMorning = LocalDateTime.of(date, LocalTime.of(12, 0));
    LocalDateTime startOfAfternoon = LocalDateTime.of(date, LocalTime.of(13, 0));
    LocalDateTime endOfAfternoon = LocalDateTime.of(date, LocalTime.of(17, 0));

    List<Booking> morningBookings = bookingRepository.findByShiftStartBetweenOrShiftEndBetween(startOfMorning, endOfMorning, startOfMorning, endOfMorning);
    int availableDesksInMorning = deskRepository.findAll().size() - morningBookings.size();

    List<Booking> afternoonBookings = bookingRepository.findByShiftStartBetweenOrShiftEndBetween(startOfAfternoon, endOfAfternoon, startOfAfternoon, endOfAfternoon);
    int availableDesksInAfternoon = deskRepository.findAll().size() - afternoonBookings.size();

    return ResponseEntity.ok(Arrays.asList(availableDesksInMorning, availableDesksInAfternoon));
  }



}
