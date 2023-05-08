package com.example.goofficebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "desk")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Desk {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id")
  private Department department;


  @OneToMany(mappedBy = "desk", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Booking> bookingList;

}
