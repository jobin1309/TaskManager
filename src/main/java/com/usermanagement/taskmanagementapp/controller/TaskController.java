package com.usermanagement.taskmanagementapp.controller;


import com.usermanagement.taskmanagementapp.dto.task.CreateTaskRequest;
import com.usermanagement.taskmanagementapp.dto.task.TaskResponse;
import com.usermanagement.taskmanagementapp.entity.Task;
import com.usermanagement.taskmanagementapp.repository.TaskRepository;
import com.usermanagement.taskmanagementapp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping()
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }



}
