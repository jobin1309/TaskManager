package com.usermanagement.taskmanagementapp.controller;

import com.usermanagement.taskmanagementapp.dto.project.CreateProjectRequest;
import com.usermanagement.taskmanagementapp.dto.project.ProjectResponse;
import com.usermanagement.taskmanagementapp.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectResponse createProject(@RequestBody CreateProjectRequest request) {
      return projectService.createProject(request);
    }

    @GetMapping("/user/{userId}")
    public List<ProjectResponse> getProjectsByUser(@PathVariable Long userId) {
        return projectService.getProjectsByUser(userId);
    }

    @GetMapping
    public String test() {
        return "Project controller working";
    }


}
