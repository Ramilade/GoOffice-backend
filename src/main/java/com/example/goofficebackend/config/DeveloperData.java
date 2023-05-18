package com.example.goofficebackend.config;

import com.example.goofficebackend.entity.*;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DepartmentRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
public class DeveloperData implements ApplicationRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DeskRepository deskRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void run(ApplicationArguments args) {

/*
        Employee employee1 = new Employee();
        employee1.setEmail("test@123.dk");
        employee1.setName("John Joe");
        employee1.setBirthdate(LocalDate.now());
        employee1.addRole(Role.ADMIN);
        employeeRepository.save(employee1);
*/

        List<Department> departmentList = createDepartments();
        List<Desk> deskList = createDesks(departmentList);
        createBooking();

    }

    private List<Department> createDepartments() {
        Department annotators = new Department();
        annotators.setName("Annotator Team");
        Department machineLearning = new Department();
        machineLearning.setName("Machine Learning Team");
        Department platform = new Department();
        platform.setName("Platform Team");
        Department sales = new Department();
        sales.setName("Sales Team");
        departmentRepository.save(annotators);
        departmentRepository.save(machineLearning);
        departmentRepository.save(platform);
        departmentRepository.save(sales);

        return departmentRepository.findAll();
    }

    private void createBooking() {

    }

    public List<Desk> createDesks(List<Department> departmentList){

        Desk desk1 = new Desk();
        Desk desk2 = new Desk();
        Desk desk3 = new Desk();
        Desk desk4 = new Desk();
        Desk desk5 = new Desk();
        Desk desk6 = new Desk();
        Desk desk7 = new Desk();

        desk1.setDepartment(departmentList.get(0));
        desk2.setDepartment(departmentList.get(0));
        desk3.setDepartment(departmentList.get(0));
        desk4.setDepartment(departmentList.get(0));
        desk5.setDepartment(departmentList.get(0));
        desk6.setDepartment(departmentList.get(0));
        desk7.setDepartment(departmentList.get(0));

        deskRepository.save(desk1);
        deskRepository.save(desk2);
        deskRepository.save(desk3);
        deskRepository.save(desk4);
        deskRepository.save(desk5);
        deskRepository.save(desk6);
        deskRepository.save(desk7);

        return deskRepository.findAll();
    }


}
