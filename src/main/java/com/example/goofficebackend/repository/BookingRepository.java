package com.example.goofficebackend.repository;

import com.example.goofficebackend.dto.BookingResponse;
import com.example.goofficebackend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

  @Query(value = "SELECT * FROM booking WHERE CAST(shift_start AS DATE) = :date", nativeQuery = true)
  List<Booking> findBookingsByDate(@Param("date") LocalDate date);

}
