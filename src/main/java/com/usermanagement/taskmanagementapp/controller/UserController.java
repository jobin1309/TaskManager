package com.usermanagement.taskmanagementapp.controller;

import com.usermanagement.taskmanagementapp.dto.auth.RegisterRequest;
import com.usermanagement.taskmanagementapp.dto.auth.UserResponse;
import com.usermanagement.taskmanagementapp.dto.project.CreateProjectRequest;
import com.usermanagement.taskmanagementapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public String test() {
        return "Users endpoint working";
    }
}
