package com.usermanagement.taskmanagementapp.controller;

import com.usermanagement.taskmanagementapp.dto.auth.AuthResponse;
import com.usermanagement.taskmanagementapp.dto.auth.LoginRequest;
import com.usermanagement.taskmanagementapp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN CONTROLLER HIT");
        return authService.login(request);
    }


    @GetMapping()
    public String getAuth() {
        return "getOK";
    }
}
