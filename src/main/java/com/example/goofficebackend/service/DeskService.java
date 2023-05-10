package com.example.goofficebackend.service;

import com.example.goofficebackend.dto.DeskResponse;
import com.example.goofficebackend.entity.Desk;
import com.example.goofficebackend.repository.DeskRepository;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeskService {

    @Autowired
    DeskRepository deskRepository;


    public List<DeskResponse> getAllDesks() {
        List<Desk> desks = deskRepository.findAll();

        return desks.stream().map(d -> new DeskResponse(d)).toList();

    }
}
