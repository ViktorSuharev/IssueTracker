package com.netcracker.edu.tms.task.service;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.repository.TaskDao;
import com.netcracker.edu.tms.task.repository.TaskRepository;
import com.netcracker.edu.tms.task.model.Priority;
import com.netcracker.edu.tms.task.model.Status;
import com.netcracker.edu.tms.task.model.Task;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private TaskRepository taskRepository;

    @Autowired
    TaskServiceImpl(TaskDao taskDao, TaskRepository taskRepository) {
        this.taskDao = taskDao;
        this.taskRepository = taskRepository;
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
        if(taskRepository.existsByProjectAndName(task.getProject(), task.getName()))
            return false;

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
        if (taskDao.getTaskById(task.getId()) == null) {
            throw new ResourceNotFoundException("task " + task.getName() + " not found");
        } else return taskDao.deleteTask(task);
    }

    @Override
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Iterable<Task> getTaskByReporter(UserWithPassword reporter) {
        return taskRepository.getAllByReporter(reporter);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Iterable<Task> getTaskByAssignee(UserWithPassword assignee) {
        return taskRepository.getAllByAssignee(assignee);
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
    public Iterable<Task> getTaskByProject(Project project) {
        return taskRepository.getAllByProject(project);
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
