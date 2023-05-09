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
    private String name;
    private String profilePic;
    private LocalDate birthdate;
    private String dietaries;

    public EmployeeResponse(Employee e) {
        this.employeeId = e.getId();
        this.email =  e.getEmail();
        this.name = e.getName();
        this.profilePic = e.getProfilePic();
        this.birthdate = e.getBirthdate();
        this.dietaries = e.getDietaries();
    }
}
