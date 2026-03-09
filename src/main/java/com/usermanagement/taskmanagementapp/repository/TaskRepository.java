package com.usermanagement.taskmanagementapp.repository;


import com.usermanagement.taskmanagementapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByProjectId(Long ProjectID);

}
