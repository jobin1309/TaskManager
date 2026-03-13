package com.usermanagement.taskmanagementapp.controller;

import com.usermanagement.taskmanagementapp.dto.auth.AuthResponse;
import com.usermanagement.taskmanagementapp.dto.auth.LoginRequest;
import com.usermanagement.taskmanagementapp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
