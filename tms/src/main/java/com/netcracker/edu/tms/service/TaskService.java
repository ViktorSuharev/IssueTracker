package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;

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

    List<Task> getTaskByReporter(BigInteger reporterId);//argument should be User instance

    List<Task> getTaskByAssignee(BigInteger assigneeId);//argument should be User instance

    List<Task> getTaskByCreationDate(String creationDate) throws ParseException;

    List<Task> getTaskByProject(BigInteger projectId);//argument should be Project instance

    List<Task> getTaskByStatus(Status taskStatus);

    List<Task> getTaskByPriority(Priority taskPriority);
}