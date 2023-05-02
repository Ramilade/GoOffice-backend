package com.example.goofficebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "end_time")
  private LocalDateTime endTime;

  @Column(name = "created")
  @CreationTimestamp
  private LocalDateTime created;

  @Column(name = "updated")
  @CreationTimestamp
  private LocalDateTime updated;
}
