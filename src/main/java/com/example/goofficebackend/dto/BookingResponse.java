package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private int bookingId;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private LocalDateTime created;
    private LocalDateTime updated;
    private EmployeeResponse employeeResponse;
    private int deskId;

    public BookingResponse(Booking b){
        this.bookingId = b.getId();
        this.shiftStart = b.getShiftStart();
        this.shiftEnd = b.getShiftEnd();
        this.created = b.getCreated();
        this.updated = b.getUpdated();
        this.employeeResponse = new EmployeeResponse(b.getEmployee());
        this.deskId = b.getDesk().getId();
    }
}
