package com.example.goofficebackend.api;

import com.example.goofficebackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LoginService loginService;

    @GetMapping("/auth-status")
    public ResponseEntity<Map<String, Object>> authStatus(Authentication authentication) {

        return loginService.authStatus(authentication);
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, String>> userInfo(Authentication authentication) {

        return loginService.userInfo(authentication);
    }

}
