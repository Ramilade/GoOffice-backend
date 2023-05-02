package com.example.goofficebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "desk")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Desk {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


}
