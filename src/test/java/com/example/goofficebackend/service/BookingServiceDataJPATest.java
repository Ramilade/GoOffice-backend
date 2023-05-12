package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.BookingResponse;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingServiceDataJPATest {

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
    employee.setName("John Doe");
    employee.setEmail("john.doe@example.com");
    employee.setBirthdate(LocalDate.now().minusYears(20));
    employee.addRole(Role.ADMIN);
    return employeeRepository.save(employee);
  }

  private Employee createTestEmployee2() {
    Employee employee = new Employee();
    employee.setName("Jane Doe");
    employee.setEmail("jane.doe@example.com");
    employee.setBirthdate(LocalDate.now().minusYears(25));
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
    LocalDateTime shiftStart = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
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
    LocalDateTime shiftStart = LocalDateTime.now().minusDays(1).withHour(9).withMinute(0);
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
  public void createBookingWithNoAvailableDeskTest() {

    //given
    LocalDateTime shiftStart = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
    LocalDateTime shiftEnd = shiftStart.plusHours(3);
    Employee testEmployee2 = createTestEmployee2();

    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setEmployeeId(testEmployee.getId());
    bookingRequest.setShiftStart(shiftStart);
    bookingRequest.setShiftEnd(shiftEnd);

    BookingRequest bookingRequest2 = new BookingRequest();
    bookingRequest2.setEmployeeId(testEmployee2.getId());
    bookingRequest2.setShiftStart(shiftStart);
    bookingRequest2.setShiftEnd(shiftEnd);

    // when
    ResponseEntity<BookingResponse> bookingResponseEntity = bookingService.createBooking(bookingRequest2);

    // then
    assertThat(bookingResponseEntity.getStatusCodeValue()).isEqualTo(200);

    // Trying to add another booking with same time and only one desk avaiable.

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      bookingService.createBooking(bookingRequest);

    });
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("No available desk during the specified time period", exception.getReason());
  }

  @Test
  public void findBookingsByDateTest() {
    //given
    Employee testEmployee2 = createTestEmployee2();
    Desk desk = new Desk();
    deskRepository.save(desk);

    LocalDateTime shiftStart = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
    LocalDateTime shiftEnd = shiftStart.plusHours(3);

    BookingRequest bookingRequest1 = new BookingRequest();
    bookingRequest1.setEmployeeId(testEmployee.getId());
    bookingRequest1.setShiftStart(shiftStart);
    bookingRequest1.setShiftEnd(shiftEnd);
    bookingService.createBooking(bookingRequest1);

    BookingRequest bookingRequest2 = new BookingRequest();
    bookingRequest2.setEmployeeId(testEmployee2.getId());
    bookingRequest2.setShiftStart(shiftStart.plusHours(3));
    bookingRequest2.setShiftEnd(shiftEnd);
    bookingService.createBooking(bookingRequest2);

    LocalDate date = LocalDate.now().plusDays(7);
    System.out.println("Test date: " + date);

    ResponseEntity<List<BookingResponse>> bookingResponses = bookingService.findBookingsByDate(date);
    assertEquals(2,
        bookingResponses.getBody().size());
  }

  @Test
  void findAvailableDesksByDate() {
    Employee testEmployee2 = createTestEmployee2();
    //given
    Desk desk1 = new Desk();
    deskRepository.save(desk1);
    Desk desk2 = new Desk();
    deskRepository.save(desk2);
    LocalDateTime shiftStart1 = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
    LocalDateTime shiftEnd1 = shiftStart1.plusHours(3);

    LocalDateTime shiftStart2 = LocalDateTime.now().plusDays(7).withHour(14).withMinute(0);
    LocalDateTime shiftEnd2 = shiftStart2.plusHours(3);

    BookingRequest bookingRequest1 = new BookingRequest();
    bookingRequest1.setEmployeeId(testEmployee.getId());
    bookingRequest1.setShiftStart(shiftStart1);
    bookingRequest1.setShiftEnd(shiftEnd1);
    bookingService.createBooking(bookingRequest1);

    BookingRequest bookingRequest2 = new BookingRequest();
    bookingRequest2.setEmployeeId(testEmployee.getId());
    bookingRequest2.setShiftStart(shiftStart2);
    bookingRequest2.setShiftEnd(shiftEnd2);
    bookingService.createBooking(bookingRequest2);

    BookingRequest bookingRequest3 = new BookingRequest();
    bookingRequest3.setEmployeeId(testEmployee2.getId());
    bookingRequest3.setShiftStart(shiftStart2);
    bookingRequest3.setShiftEnd(shiftEnd2);
    bookingService.createBooking(bookingRequest3);


    LocalDate date = LocalDate.now().plusDays(7);

    //when
    ResponseEntity<List<Integer>> availableDesks = bookingService.findAvailableDesksByDate(date);

    //then
    assertEquals(2, availableDesks.getBody().get(0));
    assertEquals(1, availableDesks.getBody().get(1));
  }

  @Test
  void checkDoubleBookingTest() {

    LocalDateTime shiftStart1 = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
    LocalDateTime shiftStart2 = LocalDateTime.now().plusDays(8).withHour(9).withMinute(0);
    LocalDateTime shiftEnd1 = shiftStart1.plusHours(3);


    BookingRequest bookingRequest1 = new BookingRequest();
    bookingRequest1.setEmployeeId(testEmployee.getId());
    bookingRequest1.setShiftStart(shiftStart1);
    bookingRequest1.setShiftEnd(shiftEnd1);
    bookingService.createBooking(bookingRequest1);

    //This one returns true because it would be a double booking.
    assertTrue(bookingService.checkDoubleBooking(shiftStart1, testEmployee.getId()));

    //This on returns false because it's not a double booking
    assertFalse(bookingService.checkDoubleBooking(shiftStart2, testEmployee.getId()));

  }

  @Test
  void deleteBookingTest() {
    LocalDateTime shiftStart1 = LocalDateTime.now().plusDays(7).withHour(9).withMinute(0);
    LocalDateTime shiftEnd1 = shiftStart1.plusHours(3);
    Desk desk = new Desk();
    desk = deskRepository.save(desk);


    Booking booking = new Booking();
    booking.setShiftStart(shiftStart1);
    booking.setShiftEnd(shiftEnd1);
    booking.setDesk(desk);
    booking.setEmployee(testEmployee);
    booking = bookingRepository.save(booking);

    ResponseEntity<String> response = bookingService.deleteBooking(booking.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Booking deleted", response.getBody());
  }

  @Test
  void deleteBookingInvalidIdTest() {
    //Not a valid id.
    int id = 100;

    assertThrows(ResponseStatusException.class, () -> bookingService.deleteBooking(id));
  }


}