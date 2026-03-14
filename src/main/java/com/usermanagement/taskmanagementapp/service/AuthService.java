package com.usermanagement.taskmanagementapp.service;

import com.usermanagement.taskmanagementapp.dto.auth.AuthResponse;
import com.usermanagement.taskmanagementapp.dto.auth.LoginRequest;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import com.usermanagement.taskmanagementapp.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);

    }

}
