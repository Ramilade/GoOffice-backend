package com.example.goofficebackend.repository;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  @Query(value = "SELECT * FROM booking WHERE CAST(shift_start AS DATE) = :date", nativeQuery = true)
  List<Booking> findBookingsByDate(@Param("date") LocalDate date);

  @Query(value = "SELECT * FROM booking WHERE CAST(shift_start AS DATE) = CAST(:date AS DATE) AND employee_id = :employeeId", nativeQuery = true)
  List<Booking> findBookingsByShiftStartAndEmployee_Id(@Param("date") LocalDateTime shiftStart,@Param("employeeId") int employee_id);

  List<Booking> findByShiftStartBetweenOrShiftEndBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, LocalDateTime startOfDay1, LocalDateTime endOfDay1);
}
