package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.BookingRequest;
import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/booking")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/findall")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return bookingService.getBookings();
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {

        return bookingService.createBooking(bookingRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable int id, @RequestBody BookingRequest bookingRequest) {

        return bookingService.updateBooking(id, bookingRequest);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {

        return bookingService.deleteBooking(id);
    }

    @GetMapping("/findbookingsbydate/{date}")
    public ResponseEntity<List<BookingResponse>> findBookingsByDate(@PathVariable LocalDate date) {

        return bookingService.findBookingsByDate(date);
    }

    @GetMapping("/findavailable/{date}")
    public ResponseEntity<List<Integer>> getAvailableDesks(@PathVariable LocalDate date) {
        return bookingService.findAvailableDesksByDate(date);
    }

}
