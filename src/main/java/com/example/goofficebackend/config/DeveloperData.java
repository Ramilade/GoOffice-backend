package com.example.goofficebackend.config;

import com.example.goofficebackend.entity.Booking;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.entity.Role;
import com.example.goofficebackend.repository.BookingRepository;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DeveloperData implements ApplicationRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DeskRepository deskRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public void run(ApplicationArguments args) {

        Employee employee1 = new Employee();
        employee1.setEmail("test@123.dk");
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setBirthdate(LocalDate.now());
        employee1.setPassword("1234");
        employee1.addRole(Role.ADMIN);
        employeeRepository.save(employee1);


        createDesks();
        createBooking();

    }

    private void createBooking() {
        Booking booking = new Booking();
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(2));
        booking.setCreated(LocalDateTime.now());
        booking.setUpdated(LocalDateTime.now());
        bookingRepository.save(booking);

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
