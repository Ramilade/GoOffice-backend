package com.example.goofficebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
@Entity
public class Employee extends EmployeeWithRoles {

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthdate;

  @Column(name = "dietaries")
  private String dietaries;

  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  @JsonBackReference
  private EmployeeWithRoles employeeWithRoles;

  public Employee (int id, String email, String firstName, String lastName, LocalDate birthdate, String dietaries){
    super(id, email);
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.dietaries = dietaries;

  }



}