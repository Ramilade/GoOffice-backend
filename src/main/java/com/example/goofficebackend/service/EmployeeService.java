package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.EmployeeRequest;
import com.example.goofficebackend.dto.EmployeeResponse;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.goofficebackend.dto.EmployeeRequest.getEmployeeEntity;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(e -> new EmployeeResponse(e)).toList();
    }

    public void createEmployeeFromGoogleAuth(EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findByEmail(employeeRequest.getEmail());
        if (employee != null) {
            updateEmployeeFromGoogleAuth(employee, employeeRequest);
        } else {

            employee = getEmployeeEntity(employeeRequest);
            employeeRepository.save(employee);
        }
    }

    public void updateEmployeeFromGoogleAuth(Employee employee, EmployeeRequest employeeRequest) {
        if (!Objects.equals(employee.getName(), employeeRequest.getName()) || !Objects.equals(employee.getEmail(), employeeRequest.getEmail()) || !Objects.equals(employee.getProfilePic(), employeeRequest.getProfilePic()))
        {
            employee.setName(employeeRequest.getName());
            employee.setEmail(employeeRequest.getEmail());
            employee.setProfilePic(employeeRequest.getProfilePic());
            employeeRepository.save(employee);
        }
    }

    public int getEmployeeIdByEmail(Authentication authentication) {

            OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String email = oauth2User.getAttribute("email");

            Employee employee = employeeRepository.findByEmail(email);
            return employee.getId();
        }


}
