package com.netcracker.edu.tms.task.service;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.History;
import com.netcracker.edu.tms.task.model.Priority;
import com.netcracker.edu.tms.task.model.Status;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.User;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

public interface TaskService {

    Task getTaskById(BigInteger taskId);

    List<Task> getTaskByName(String taskName);

    boolean addTask(Task task);

    boolean updateTask(Task task, String comment, User updater);

    boolean deleteTask(Task task);

    Iterable<Task> getAll();

    Iterable<Task> getTaskByReporter(User reporter);//argument should be UserWithPassword instance

    Iterable<Task> getTaskByAssignee(User assignee);//argument should be UserWithPassword instance

    List<Task> getTaskByCreationDate(String creationDate) throws ParseException;

    Iterable<Task> getTaskByProject(Project project);

    List<Task> getTaskByStatus(Status taskStatus);

    List<Task> getTaskByPriority(Priority taskPriority);

    Iterable<History> getHistoryByTaskId(BigInteger id);

    Iterable<Task> getActiveTasksByAssignee(User assignee);

    void deleteAllTasksByProject(Project project);

    Iterable<Task> getActiveTasksByProject(Project project);

    Iterable<Task> getResolvedTasksByProject(Project project);

    Iterable<Task> getAllActiveTasks();

    Iterable<Task> getResolvedTasksByAssignee(User assignee);

    Iterable<Task> getResolvedTasksByReporter(User reporter);

    Iterable<Task> getClosedTasksByAssignee(User assignee);
}