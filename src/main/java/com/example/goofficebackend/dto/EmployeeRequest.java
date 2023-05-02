package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest {

    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String dietaries;

    public EmployeeRequest(Employee e){
        this.email = e.getEmail();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.birthdate = e.getBirthdate();
        this.dietaries = e.getDietaries();
    }

}
