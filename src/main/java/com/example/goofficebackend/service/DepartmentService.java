package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.DepartmentResponse;
import com.example.goofficebackend.entity.Department;
import com.example.goofficebackend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream().map(d -> new DepartmentResponse(d)).toList();
    }
}
