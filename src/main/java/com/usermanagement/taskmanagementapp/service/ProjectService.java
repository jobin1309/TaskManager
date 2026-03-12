package com.usermanagement.taskmanagementapp.service;


import com.usermanagement.taskmanagementapp.dto.project.CreateProjectRequest;
import com.usermanagement.taskmanagementapp.dto.project.ProjectResponse;
import com.usermanagement.taskmanagementapp.entity.Project;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.ProjectRepository;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(CreateProjectRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUser(user);
        Project savedProject = projectRepository.save(project);
        return mapToProjectResponse(savedProject);
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjectId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setUserId(project.getUser().getId());
        projectResponse.setDescription(project.getDescription());
        return projectResponse;
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        return projects.stream()
                .map(this::mapToProjectResponse)
                .toList();
    }
}

