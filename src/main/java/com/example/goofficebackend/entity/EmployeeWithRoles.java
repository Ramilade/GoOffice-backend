package com.example.goofficebackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee_with_roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmployeeWithRoles {

  @Id
  @Column(nullable = false,length = 50,unique = true)
  String email;

  //60 = length of a bcrypt encoded password
  @Column(nullable = false, length = 60)
  String password;

  @CreationTimestamp
  private LocalDateTime created;

  @UpdateTimestamp
  private LocalDateTime edited;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "ENUM('USER','ADMIN')")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "security_role")
  List<Role> roles = new ArrayList<>();

  @OneToOne(mappedBy = "employeeWithRoles", cascade = CascadeType.ALL)
  private Employee employee;

}