package com.dev.microservices.authservice.controller;


import com.dev.microservices.authservice.dto.LoginRequest;
import com.dev.microservices.authservice.dto.LoginResponse;
import com.dev.microservices.authservice.dto.RegisterRequest;
import com.dev.microservices.authservice.dto.RegisterResponse;
import com.dev.microservices.authservice.response.ApiResponse;
import com.dev.microservices.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<RegisterResponse>builder()
                .success(true)
                .message("Created")
                .data(registerResponse)
                .timestamp(LocalDateTime.now()).build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successfully")
                .data(loginResponse)
                .timestamp(LocalDateTime.now()).build()
        );
    }


}
