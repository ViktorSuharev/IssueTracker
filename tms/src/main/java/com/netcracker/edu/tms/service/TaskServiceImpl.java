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

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Task getTaskById(BigInteger taskId) {
        return taskDao.getTaskById(taskId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByName(String taskName) {
        return taskDao.listOfTasksByName(taskName);
    }

    @Override
    @Transactional
    public boolean addTask(Task task) {
        task.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
        task.setStatus(Status.NOT_STARTED);
        return taskDao.addTask(task);
    }

    @Override
    @Transactional(propagation =  Propagation.REQUIRES_NEW)
    public boolean updateTask(Task task) {
        task.setModificationDate(new java.sql.Date(System.currentTimeMillis()));
        return taskDao.updateTask(task);
    }

    @Override
    @Transactional(propagation =  Propagation.REQUIRES_NEW)
    public boolean deleteTask(Task task) {
        if(taskDao.getTaskById(task.getTaskId()) == null){
            throw new ResourceNotFoundException("task "+task.getTaskName()+" not found");
        }
        else return taskDao.deleteTask(task);
}

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByReporter(BigInteger reporterId) {
            return taskDao.listOfTasksByReporter(reporterId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByAssignee(BigInteger assigneeId) {
        return taskDao.listOfTasksByAssignee(assigneeId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByCreationDate(String creationDate) throws ParseException {
        SimpleDateFormat datePattern = new SimpleDateFormat("dd-MM-yyyy");
        Date date = datePattern.parse(creationDate);
        return taskDao.listOfTasksByCreationDate(date);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByProject(BigInteger projectId) {
        return taskDao.listOfTasksByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByStatus(Status taskStatus) {
        return taskDao.listOfTasksByStatus(taskStatus);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Task> listOfTasksByPriority(Priority taskPriority) {
        return taskDao.listOfTasksByPriority(taskPriority);
    }
}
