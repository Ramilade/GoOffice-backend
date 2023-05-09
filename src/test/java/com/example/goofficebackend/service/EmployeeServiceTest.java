package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.EmployeeRequest;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    EmployeeService employeeService;

    Employee testEmployee = new Employee();


    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository);
        testEmployee.setEmail("firstuser@gmail.com");
        testEmployee.setName("firstuser");
        testEmployee.setProfilePic("firstuserprofilepic");
        employeeRepository.save(testEmployee);
    }

    @Test
    void createEmployeeFromGoogleAuth() {
        String email = "testemail@gmail.com";
        String name = "testname";
        String profilePic = "testprofilepic";
        EmployeeRequest employeeRequest = new EmployeeRequest(email, name, profilePic);
        employeeService.createEmployeeFromGoogleAuth(employeeRequest);

        assertEquals(employeeRepository.findByEmail(email).getEmail(), email);
        assertEquals(employeeRepository.findByEmail(email).getName(), name);
        assertEquals(employeeRepository.findByEmail(email).getProfilePic(), profilePic);
    }

    @Test
    void createEmployeeThatExistFromGoogleAuth() {
        String email = "firstuser@gmail.com";
        String name = "newtestname";
        String profilePic = "newtestprofilepic";
        EmployeeRequest employeeRequest = new EmployeeRequest(email, name, profilePic);
        employeeService.createEmployeeFromGoogleAuth(employeeRequest);

        assertEquals(employeeRepository.findByEmail(email).getName(), name);
        assertEquals(employeeRepository.findByEmail(email).getProfilePic(), profilePic);
    }

}