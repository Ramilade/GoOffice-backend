package com.example.goofficebackend.service;

import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<Map<String, Object>> authStatus(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        response.put("isAuthenticated", isAuthenticated);


        if (isAuthenticated) {
            OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String email = oauth2User.getAttribute("email");
            Employee employee = employeeRepository.findByEmail(email);
            if (employee != null) {
                response.put("email", email);
                response.put("name", employee.getName());
                response.put("profilePic", employee.getProfilePic());
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "User not found. Please contact admin.");
                return ResponseEntity.badRequest().body(response);
            }
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> userInfo(Authentication authentication) {
        Map<String, String> userInfo = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String pictureUrl = oauth2User.getAttribute("picture");

            userInfo.put("email", email);
            userInfo.put("name", name);
            userInfo.put("profilePic", pictureUrl);
        }

        return ResponseEntity.ok(userInfo);

    }


}
