package com.example.goofficebackend.api;

import com.example.goofficebackend.dto.DepartmentResponse;
import com.example.goofficebackend.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/findall")
    public List<DepartmentResponse> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
}
