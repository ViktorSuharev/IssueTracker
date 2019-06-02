package com.netcracker.edu.tms.task.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.Status;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TaskRepository extends CrudRepository<Task, BigInteger> {
    boolean existsByProjectAndName(Project project, String name);

    Iterable<Task> findAllByAssignee(User assignee);

    Iterable<Task> findAllByAssigneeAndStatus(User assignee, Status status);

    Iterable<Task> findAllByReporter(User reporter);

    Iterable<Task> findAllByProject(Project project);

    Iterable<Task> findAllByProjectAndStatus(Project project, Status status);

    void deleteAllByProject(Project project);

    Iterable<Task> findAllByStatus(Status status);

    Iterable<Task> findAllByReporterAndStatus(User reporter, Status resolved);

    Iterable<Task> findByNameContainingIgnoreCase(String namePattern);

    Iterable<Task> findTop2ByNameContainingIgnoreCase(String namePattern);
}
