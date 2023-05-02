package com.example.goofficebackend.repository;

import com.example.goofficebackend.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk,Integer> {
}
