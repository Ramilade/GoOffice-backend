package com.example.goofficebackend.entity;

import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "end_time")
  private LocalTime endTime;

  @Column(name = "created")
  @CreationTimestamp
  private LocalDateTime created;

  @Column(name = "updated")
  @UpdateTimestamp
  private LocalDateTime updated;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "desk_id")
  private Desk desk;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
