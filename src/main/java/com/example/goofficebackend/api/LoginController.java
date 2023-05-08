package com.example.goofficebackend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {

    @GetMapping("/auth-status")
    public ResponseEntity<Map<String, Object>> authStatus(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return ResponseEntity.ok(response);
    }
}
