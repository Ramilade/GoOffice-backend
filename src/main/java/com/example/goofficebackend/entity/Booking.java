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

  @Column(name = "shift_start")
  private LocalDateTime shiftStart;

  @Column(name = "shift_end")
  private LocalDateTime shiftEnd;

  @Column(name = "created")
  @CreationTimestamp
  private LocalDateTime created;

  @Column(name = "updated")
  @UpdateTimestamp
  private LocalDateTime updated;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "desk_id")
  private Desk desk;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Override
  public String toString() {
    return "Booking{" +
            "id=" + id +
            ", shiftStart=" + shiftStart +
            ", shiftEnd=" + shiftEnd +
            ", created=" + created +
            ", updated=" + updated +
            ", desk=" + desk +
            ", employee=" + employee +
            '}';
  }
}
