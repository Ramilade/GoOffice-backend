package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.entity.EmployeeWithRoles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeWithRolesResponse {

    private int id;
    private String email;


    public EmployeeWithRolesResponse(EmployeeWithRoles e) {
        this.id = e.getId();
        this.email = e.getEmail();
    }

}
