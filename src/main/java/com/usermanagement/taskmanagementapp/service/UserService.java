package com.usermanagement.taskmanagementapp.service;

import com.usermanagement.taskmanagementapp.dto.auth.RegisterRequest;
import com.usermanagement.taskmanagementapp.dto.auth.UserResponse;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);

    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setUserId(user.getId());
        return userResponse;
    }

}
