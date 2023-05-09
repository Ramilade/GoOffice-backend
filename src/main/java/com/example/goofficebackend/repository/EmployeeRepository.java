package com.example.goofficebackend.repository;

import com.example.goofficebackend.dto.EmployeeResponse;
import com.example.goofficebackend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByEmail(String email);
}
