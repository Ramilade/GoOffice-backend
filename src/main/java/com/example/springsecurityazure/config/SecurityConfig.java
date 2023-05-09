package com.example.springsecurityazure.config;

import com.example.goofficebackend.service.EmployeeService;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    EmployeeService employeeService;

    public SecurityConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> {
                    auth.requestMatchers(req -> req.getRequestURI().equals("/") || req.getRequestURI().equals("/auth-status") || req.getRequestURI().startsWith("/oauth2/authorization/")).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginProcessingUrl("/login/oauth2/code/*") // Add this line
                        .defaultSuccessUrl("http://127.0.0.1:5500", true)
                        .successHandler(new CustomAuthenticationSuccessHandler(employeeService))) // Use the custom success handler
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Remove the cookie when logging out
                            Cookie sessionCookie = new Cookie("JSESSIONID", null);
                            sessionCookie.setPath("/");
                            sessionCookie.setMaxAge(0);
                            sessionCookie.setHttpOnly(true);
                            sessionCookie.setSecure(true);
                            response.addCookie(sessionCookie);
                            response.setStatus(HttpStatus.OK.value());
                            response.setHeader("Location", "https://accounts.google.com/Logout?&continue=http://127.0.0.1:5500");
                        })
                        .logoutUrl("/logout")
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(request -> "/logout".equals(request.getRequestURI()))) // Disable CSRF protection for /logout endpoint
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setExposedHeaders(List.of("Set-Cookie"));

                            return corsConfiguration;
                        })) // Enable CORS
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/"))
                .build();
    }
}


