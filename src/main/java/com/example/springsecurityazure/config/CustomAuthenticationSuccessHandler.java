package com.example.springsecurityazure.config;

import com.example.goofficebackend.dto.EmployeeRequest;
import com.example.goofficebackend.dto.EmployeeResponse;
import com.example.goofficebackend.entity.Employee;
import com.example.goofficebackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    EmployeeService employeeService;

    public CustomAuthenticationSuccessHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String profilePic = oauth2User.getAttribute("picture"); // Retrieve profile picture URL

        EmployeeRequest employeeRequest = new EmployeeRequest(email,name,profilePic);
        employeeService.createEmployeeFromGoogleAuth(employeeRequest);

        String targetUrl = "https://thankful-coast-0e5d88710.3.azurestaticapps.net/#/";
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
