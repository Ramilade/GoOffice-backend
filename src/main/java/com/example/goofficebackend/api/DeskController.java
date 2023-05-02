package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.DeskResponse;
import com.example.goofficebackend.service.DeskService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/desk")
public class DeskController {

    DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @GetMapping("/findall")
    public List<DeskResponse> getAllDesks() {
        return deskService.getAllDesks();
    }


}
