package com.usermanagement.taskmanagementapp.service;


import com.usermanagement.taskmanagementapp.dto.project.CreateProjectRequest;
import com.usermanagement.taskmanagementapp.dto.project.ProjectResponse;
import com.usermanagement.taskmanagementapp.entity.Project;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.ProjectRepository;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String userEmail = auth.getName();

        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUser(user);
        Project savedProject = projectRepository.save(project);
        return mapToProjectResponse(savedProject);
    }

    public void deleteProject(Long projectId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String userEmail = auth.getName();

        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow( () ->  new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId).orElseThrow(() ->
                new RuntimeException(" Project not found"));

        if(user.getId().equals(project.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
       projectRepository.deleteById(projectId);
    }


    private ProjectResponse mapToProjectResponse(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        Long userId = project.getUser().getId();
        System.out.println(userId);
        projectResponse.setProjectId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setUserId(userId);
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

