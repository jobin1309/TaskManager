package com.usermanagement.taskmanagementapp.service;

import com.usermanagement.taskmanagementapp.dto.auth.RegisterRequest;
import com.usermanagement.taskmanagementapp.dto.auth.UserResponse;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);

    }


    public List<UserResponse> getAllUsers() {
       List<User> users = userRepository.findAll();
       return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setUserId(user.getId());
        return userResponse;
    }

}
