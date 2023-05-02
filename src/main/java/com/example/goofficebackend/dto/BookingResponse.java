package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Booking;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private int booking_id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    public BookingResponse(Booking b){
        this.booking_id = b.getId();
        this.startTime = b.getStartTime();
        this.endTime = b.getEndTime();
        this.created = b.getCreated();
        this.updated = b.getUpdated();
    }
}
