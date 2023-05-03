package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {

    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int employeeId;

    public BookingRequest(LocalDateTime shiftStart, LocalDateTime shiftEnd, int employeeId) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.employeeId = employeeId;
    }

    public BookingRequest(Booking b){
        this.shiftStart = b.getShiftStart();
        this.shiftEnd = b.getShiftEnd();
        this.created = b.getCreated();
        this.updated = b.getUpdated();
        this.employeeId = b.getEmployee().getId();
    }

}
