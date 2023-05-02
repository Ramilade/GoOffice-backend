package com.example.goofficebackend.dto;


import com.example.goofficebackend.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentResponse {

    private int id;
    private String name;

    public DepartmentResponse(Department d){
        this.id = d.getId();
        this.name = d.getName();
    }

}
