package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private int booking_id;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;

    private LocalDateTime created;

    private LocalDateTime updated;

    public BookingResponse(Booking b){
        this.booking_id = b.getId();
        this.startDate = b.getStartDate();
        this.startTime = b.getStartTime().truncatedTo(ChronoUnit.MINUTES);
        this.endDate = b.getEndDate();
        this.endTime = b.getEndTime().truncatedTo(ChronoUnit.MINUTES);
        this.created = b.getCreated();
        this.updated = b.getUpdated();
    }
}
