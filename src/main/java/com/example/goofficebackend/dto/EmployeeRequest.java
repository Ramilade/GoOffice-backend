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
    private String name;
    private String profilePic;
    private LocalDate birthdate;
    private String dietaries;

    public EmployeeRequest(Employee e){
        this.email = e.getEmail();
        this.name = e.getName();
        this.birthdate = e.getBirthdate();
        this.dietaries = e.getDietaries();
    }

    public static Employee getEmployeeEntity (EmployeeRequest e){
        return new Employee(e.email,e.name,e.profilePic);
    }

    public EmployeeRequest(String email, String name, String profilePic) {
        this.email = email;
        this.name = name;
        this.profilePic = profilePic;
    }
}
