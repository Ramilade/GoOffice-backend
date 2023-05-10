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

  @Column(name = "name", length = 150)
  private String name;

  @Column(nullable = true)
  private String profilePic;

  @Column(name = "birth_date")
  private LocalDate birthdate;

  @Column(name = "dietaries")
  private String dietaries;

  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  @JsonBackReference
  private EmployeeWithRoles employeeWithRoles;

  public Employee (int id, String email, String name, LocalDate birthdate, String dietaries){
    super(id, email);
    this.name = name;
    this.birthdate = birthdate;
    this.dietaries = dietaries;

  }


  public Employee(String email, String name, String profilePic) {
    this.email = email;
    this.name = name;
    this.profilePic = profilePic;
    addRole(Role.USER);
  }

  @Override
  public String toString() {
    return "Employee{" +
            "name='" + name + '\'' +
            '}';
  }
}