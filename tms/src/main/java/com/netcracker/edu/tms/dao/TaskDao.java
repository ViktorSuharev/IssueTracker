package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface TaskDao {

    Task getTaskById(BigInteger taskId);

    List<Task> getTaskByName(String taskName);

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean deleteTask(Task task);

    List<Task> getTaskByReporter(BigInteger reporterId);//argument should be User instance

    List<Task> getTaskByAssignee(BigInteger assigneeId);//argument should be User instance

    List<Task> getTaskByCreationDate(Date creationDate);

    List<Task> getTaskByProject(BigInteger projectId);//argument should be Project instance

    List<Task> getTaskByStatus(Status taskStatus);

    List<Task> getTaskByPriority(Priority taskPriority);


}
