package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.dao.TaskDao;
import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Autowired
    TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Task getTaskById(BigInteger taskId) {
        return taskDao.getTaskById(taskId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByName(String taskName) {
        return taskDao.getTaskByName(taskName);
    }

    @Override
    @Transactional
    public boolean addTask(Task task) {
        task.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
        task.setStatus(Status.NOT_STARTED);
        return taskDao.addTask(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateTask(Task task) {
        task.setModificationDate(new java.sql.Date(System.currentTimeMillis()));
        return taskDao.updateTask(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteTask(Task task) {
        if (taskDao.getTaskById(task.getTaskId()) == null) {
            throw new ResourceNotFoundException("task " + task.getTaskName() + " not found");
        } else return taskDao.deleteTask(task);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByReporter(BigInteger reporterId) {
        return taskDao.getTaskByReporter(reporterId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByAssignee(BigInteger assigneeId) {
        return taskDao.getTaskByAssignee(assigneeId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByCreationDate(String creationDate) throws ParseException {
        SimpleDateFormat datePattern = new SimpleDateFormat("dd-MM-yyyy");
        Date date = datePattern.parse(creationDate);
        return taskDao.getTaskByCreationDate(date);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByProject(BigInteger projectId) {
        return taskDao.getTaskByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByStatus(Status taskStatus) {
        return taskDao.getTaskByStatus(taskStatus);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> getTaskByPriority(Priority taskPriority) {
        return taskDao.getTaskByPriority(taskPriority);
    }
}
