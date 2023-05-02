package com.example.goofficebackend.config;

import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DeveloperData implements ApplicationRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DeskRepository deskRepository;

    @Override
    public void run(ApplicationArguments args) {

        Employee employee1 = new Employee();
        employee1.setEmail("test@123.dk");
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setBirthdate(LocalDate.now());
        employeeRepository.save(employee1);

        createDesks();

    }

    public void createDesks(){
        Desk desk1 = new Desk();
        Desk desk2 = new Desk();
        Desk desk3 = new Desk();
        Desk desk4 = new Desk();
        Desk desk5 = new Desk();
        Desk desk6 = new Desk();
        Desk desk7 = new Desk();

        deskRepository.save(desk1);
        deskRepository.save(desk2);
        deskRepository.save(desk3);
        deskRepository.save(desk4);
        deskRepository.save(desk5);
        deskRepository.save(desk6);
        deskRepository.save(desk7);

    }


}
