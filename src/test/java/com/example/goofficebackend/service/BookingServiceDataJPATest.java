package com.example.goofficebackend.service;

import com.example.goofficebackend.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.goofficebackend.dto.BookingRequest;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingServiceDataJPATest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DeskRepository deskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private BookingService bookingService;

    private Employee testEmployee;

    @BeforeEach
    public void setup() {
        bookingService = new BookingService(bookingRepository, deskRepository, employeeRepository);

        testEmployee = createUser();
        createDesk();
    }

    private Employee createUser() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setBirthdate(LocalDate.now().minusYears(20));
        employee.addRole(Role.ADMIN);
        return employeeRepository.save(employee);
    }

    private void createDesk() {
        Desk desk = new Desk();
        deskRepository.save(desk);
    }

    @Test
    public void createBookingTest() {

        //given
        LocalDateTime shiftStart = LocalDateTime.now().plusDays(1);
        LocalDateTime shiftEnd = shiftStart.plusHours(3);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmployeeId(testEmployee.getId());
        bookingRequest.setShiftStart(shiftStart);
        bookingRequest.setShiftEnd(shiftEnd);

        // when
        ResponseEntity<?> bookingResponseEntity = bookingService.createBooking(bookingRequest);

        // then
        assertThat(bookingResponseEntity.getStatusCodeValue()).isEqualTo(200);

        Booking createdBooking = bookingRepository.findAll().get(0);

        assertThat(createdBooking.getShiftStart()).isEqualTo(shiftStart);
        assertThat(createdBooking.getShiftEnd()).isEqualTo(shiftEnd);
    }

    @Test
    public void createBookingFailTest() {

        //given
        LocalDateTime shiftStart = LocalDateTime.now().minusDays(1);
        LocalDateTime shiftEnd = shiftStart.plusDays(1);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmployeeId(testEmployee.getId());
        bookingRequest.setShiftStart(shiftStart);
        bookingRequest.setShiftEnd(shiftEnd);

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            bookingService.createBooking(bookingRequest);

        });
        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Start and end date is not the same", exception.getReason());

    }

    @Test
    public void createBookingWithNoAvailableDesk() {
        //given
        LocalDateTime shiftStart = LocalDateTime.now().plusHours(1);
        LocalDateTime shiftEnd = LocalDateTime.now().plusHours(3);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setEmployeeId(testEmployee.getId());
        bookingRequest.setShiftStart(shiftStart);
        bookingRequest.setShiftEnd(shiftEnd);

        BookingRequest bookingRequest2 = new BookingRequest();
        bookingRequest2.setEmployeeId(testEmployee.getId());
        bookingRequest2.setShiftStart(shiftStart);
        bookingRequest2.setShiftEnd(shiftEnd);

        // when
        ResponseEntity<?> bookingResponseEntity = bookingService.createBooking(bookingRequest);

        // then
        assertThat(bookingResponseEntity.getStatusCodeValue()).isEqualTo(200);

        // Trying to add another booking with same time and only one desk avaiable.

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            bookingService.createBooking(bookingRequest);

        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("No available desk during the specified time period", exception.getReason());

    }
}
