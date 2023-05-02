package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/booking")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/findall")
    public List<BookingResponse> getBookings() {
        return bookingService.getBookings();
    }

    @PostMapping("/create/{deskId}/{employeeId}/{startTime}/{endTime}")
    public BookingResponse createBooking(@PathVariable("deskId") int deskId,
                                         @PathVariable("employeeId") int employeeId,
                                         @PathVariable("startDate") LocalDate startDate,
                                         @PathVariable("startTime") LocalTime startTime,
                                         @PathVariable("endDate") LocalDate endDate,
                                         @PathVariable("endTime") LocalTime endTime) {
        Booking booking = bookingService.createBooking(deskId, employeeId, startDate, startTime, endDate, endTime);

        return new BookingResponse(booking);
    }
}
