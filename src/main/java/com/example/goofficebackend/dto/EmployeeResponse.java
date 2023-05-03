package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.entity.EmployeeWithRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {

    private int employeeId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String dietaries;

    public EmployeeResponse(Employee e) {
        this.employeeId = e.getEmployeeWithRoles().getId();
        this.email =  e.getEmail();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.birthdate = e.getBirthdate();
        this.dietaries = e.getDietaries();
    }
}
