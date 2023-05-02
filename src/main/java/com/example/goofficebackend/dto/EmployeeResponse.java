package com.example.goofficebackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;
    private String dietaries;

    /*    public EmployeeResponse(Employee e){
        this.id = e.getId();
        this.email = e.getEmail();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.birthdate = e.getBirthDate();
        this.dietaries = e.getDietaries();
    }*/

}
