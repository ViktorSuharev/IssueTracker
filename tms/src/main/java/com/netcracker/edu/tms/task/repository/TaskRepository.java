package com.netcracker.edu.tms.task.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TaskRepository extends CrudRepository<Task, BigInteger> {
    boolean existsByProjectAndName(Project project, String name);

    Iterable<Task> getAllByAssignee(User assignee);

    Iterable<Task> getAllByReporter(User reporter);

    Iterable<Task> getAllByProject(Project project);
}