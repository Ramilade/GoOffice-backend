package com.example.goofficebackend.repository;

import com.example.goofficebackend.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk,Integer> {

    @Query(value = "SELECT * FROM desk AS d WHERE NOT EXISTS (SELECT * FROM booking AS b WHERE b.desk_id = d.id AND ((b.shift_start >= :start AND b.shift_start < :end) OR (b.shift_end > :start AND b.shift_end <= :end) OR (b.shift_start <= :start AND b.shift_end >= :end))) LIMIT 1", nativeQuery = true)
    Optional<Desk> findAvailableDesk(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
