package com.example.goofficebackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest {

    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;
    private String dietaries;

/*    public EmployeeRequest(Employee e){
        this.email = e.getEmail();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.birthdate = e.getBirthDate();
        this.dietaries = e.getDietaries();
    }*/

}
