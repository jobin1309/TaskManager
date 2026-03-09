package com.usermanagement.taskmanagementapp.service;


import com.usermanagement.taskmanagementapp.dto.task.CreateTaskRequest;
import com.usermanagement.taskmanagementapp.dto.task.TaskResponse;
import com.usermanagement.taskmanagementapp.entity.Project;
import com.usermanagement.taskmanagementapp.entity.Task;
import com.usermanagement.taskmanagementapp.repository.ProjectRepository;
import com.usermanagement.taskmanagementapp.repository.TaskRepository;
import com.usermanagement.taskmanagementapp.utils.TaskStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow();

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

        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return response;
    }
}