package com.example.goofficebackend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        response.put("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String email = oauth2User.getAttribute("email");
            response.put("email", email);
        }

        return ResponseEntity.ok(response);
    }

}
