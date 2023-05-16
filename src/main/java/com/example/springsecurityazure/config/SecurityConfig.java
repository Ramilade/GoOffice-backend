package com.example.springsecurityazure.config;

import com.example.goofficebackend.service.EmployeeService;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                    auth
                            .requestMatchers(req -> req.getRequestURI().equals("/") || req.getRequestURI().equals("/auth-status") || req.getRequestURI().startsWith("/oauth2/authorization/")).permitAll()
                            .requestMatchers(req -> req.getRequestURI().startsWith("/api/")).authenticated(); // Add this line to allow authenticated users to access /api endpoints
                })
//                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginProcessingUrl("/login/oauth2/code/*") // Add this line
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
//                            response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
                            response.setHeader("Access-Control-Allow-Origin", "https://thankful-coast-0e5d88710.3.azurestaticapps.net");
                            response.setStatus(HttpStatus.OK.value());
                            response.setHeader("Location", "https://accounts.google.com/Logout?&continue=https://thankful-coast-0e5d88710.3.azurestaticapps.net");
                        })
                        .logoutUrl("/logout")
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/api/**"), // Add this line to disable CSRF protection for /api endpoints
                                new AntPathRequestMatcher("/logout") // Disable CSRF protection for /logout endpoint
                        )
                )
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
//                            corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
                            corsConfiguration.setAllowedOrigins(List.of("https://thankful-coast-0e5d88710.3.azurestaticapps.net"));
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
                            corsConfiguration.setExposedHeaders(List.of("Set-Cookie"));

                            return corsConfiguration;
                        }))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/"))
                .build();
    }
}


