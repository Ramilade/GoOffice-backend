package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.BookingRequest;
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

    @PostMapping("/create")
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {

        Booking booking = bookingService.createBooking(bookingRequest);

        return new BookingResponse(booking);
    }

    @PutMapping("/update/{id}")
    public BookingResponse updateBooking(@PathVariable int id, @RequestBody BookingRequest bookingRequest) {

        Booking booking = bookingService.updateBooking(id, bookingRequest);

        return new BookingResponse(booking);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBooking(@PathVariable int id) {

        bookingService.deleteBooking(id);
    }

}
