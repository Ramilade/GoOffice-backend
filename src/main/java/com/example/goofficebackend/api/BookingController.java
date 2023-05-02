package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/booking")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/findall")
    public List<BookingResponse> getBookings(){
        return bookingService.getBookings();
    }
}
