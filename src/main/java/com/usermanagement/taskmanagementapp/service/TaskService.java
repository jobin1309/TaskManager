package com.usermanagement.taskmanagementapp.service;


import com.usermanagement.taskmanagementapp.dto.task.CreateTaskRequest;
import com.usermanagement.taskmanagementapp.dto.task.TaskResponse;
import com.usermanagement.taskmanagementapp.entity.Project;
import com.usermanagement.taskmanagementapp.entity.Task;
import com.usermanagement.taskmanagementapp.entity.User;
import com.usermanagement.taskmanagementapp.repository.ProjectRepository;
import com.usermanagement.taskmanagementapp.repository.TaskRepository;
import com.usermanagement.taskmanagementapp.repository.UserRepository;
import com.usermanagement.taskmanagementapp.utils.TaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        if(!project.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Unauthorized access to the project");
        }

        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setProject(project);

        task.setStatus(TaskStatus.TODO);
        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);

    }


    private TaskResponse mapToResponse(Task task) {

        TaskResponse response = new TaskResponse();
        response.setId(task.getTaskId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return response;
    }
}