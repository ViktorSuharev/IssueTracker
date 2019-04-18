package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.*;

import java.math.BigInteger;
import java.util.Date;
import java.text.ParseException;
import java.util.List;

public interface TaskService {

    Task getTaskById(BigInteger taskId);

    List<Task> getTaskByName(String taskName);

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean deleteTask(Task task);

    Iterable<Task> getAll();

    Iterable<Task> getTaskByReporter(User reporter);//argument should be User instance

    Iterable<Task> getTaskByAssignee(User assignee);//argument should be User instance

    List<Task> getTaskByCreationDate(String creationDate) throws ParseException;

    Iterable<Task> getTaskByProject(Project project);

    List<Task> getTaskByStatus(Status taskStatus);

    List<Task> getTaskByPriority(Priority taskPriority);
}