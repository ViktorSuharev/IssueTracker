package com.netcracker.edu.tms.task.service;

import com.netcracker.edu.tms.mail.model.Mail;
import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.History;
import com.netcracker.edu.tms.task.model.Priority;
import com.netcracker.edu.tms.task.model.Status;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.task.repository.HistoryRepository;
import com.netcracker.edu.tms.task.repository.TaskDao;
import com.netcracker.edu.tms.task.repository.TaskRepository;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import io.jsonwebtoken.lang.Collections;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;
    private TaskRepository taskRepository;
    private HistoryRepository historyRepository;
    private MailService mailService;
    private UserService userService;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskRepository taskRepository,
                           HistoryRepository historyRepository, MailService mailService,
                           UserService userService) {
        this.taskDao = taskDao;
        this.taskRepository = taskRepository;
        this.historyRepository = historyRepository;
        this.mailService = mailService;
        this.userService = userService;
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
        if (taskRepository.existsByProjectAndName(task.getProject(), task.getName()))
            return false;

        task.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
        task.setStatus(Status.NOT_STARTED);
        return taskDao.addTask(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateTask(Task task, String comment, User author) {
        Task t = taskRepository.findById(task.getId()).get();
        task.setCreationDate(t.getCreationDate());

        History history = new History(task.getId(), comment, author, new java.sql.Date(System.currentTimeMillis()));
        historyRepository.save(history);

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
    public Iterable<Task> getTaskByReporter(User reporter) {
        return taskRepository.findAllByReporter(reporter);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Iterable<Task> getTaskByAssignee(User assignee) {
        return taskRepository.findAllByAssignee(assignee);
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
        return taskRepository.findAllByProject(project);
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

    @Override
    public Iterable<History> getHistoryByTaskId(BigInteger id) {
        return historyRepository.findAllByTaskId(id);
    }

    @Override
    public Iterable<Task> getActiveTasksByAssignee(User assignee) {
        Iterable<Task> notStartedTasks = taskRepository.findAllByAssigneeAndStatus(assignee, Status.NOT_STARTED);
        Iterable<Task> inProgressTasks = taskRepository.findAllByAssigneeAndStatus(assignee, Status.IN_PROGRESS);

        return Stream.concat(
                StreamSupport.stream(notStartedTasks.spliterator(), false),
                StreamSupport.stream(inProgressTasks.spliterator(), false))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllTasksByProject(Project project) {
        taskRepository.deleteAllByProject(project);
    }

    @Override
    public Iterable<Task> getActiveTasksByProject(Project project) {
        Iterable<Task> notStartedTasks = taskRepository.findAllByProjectAndStatus(project, Status.NOT_STARTED);
        Iterable<Task> inProgressTasks = taskRepository.findAllByProjectAndStatus(project, Status.IN_PROGRESS);

        return Stream.concat(
                StreamSupport.stream(notStartedTasks.spliterator(), false),
                StreamSupport.stream(inProgressTasks.spliterator(), false))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Task> getResolvedTasksByProject(Project project) {
        return taskRepository.findAllByProjectAndStatus(project, Status.RESOLVED);
    }

    @Override
    public Iterable<Task> getAllActiveTasks() {
        Iterable<Task> notStartedTasks = taskRepository.findAllByStatus(Status.NOT_STARTED);
        Iterable<Task> inProgressTasks = taskRepository.findAllByStatus(Status.IN_PROGRESS);

        return Stream.concat(
                StreamSupport.stream(notStartedTasks.spliterator(), false),
                StreamSupport.stream(inProgressTasks.spliterator(), false))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Task> getResolvedTasksByAssignee(User user) {
        return taskRepository.findAllByAssigneeAndStatus(user, Status.RESOLVED);
    }

    @Override
    public Iterable<Task> getResolvedTasksByReporter(User reporter) {
        return taskRepository.findAllByReporterAndStatus(reporter, Status.RESOLVED);
    }

    @Override
    public Iterable<Task> getClosedTasksByAssignee(User assignee) {
        return taskRepository.findAllByAssigneeAndStatus(assignee, Status.CLOSED);
    }

    @Override
    public void sendNewTaskToAssignee(Task newTask) {
        List<String> assigneeEmail = new ArrayList<>();
        assigneeEmail.add(newTask.getAssignee().getEmail());
        if (!userService.existsByEmail(assigneeEmail.get(0)))
            throw new IllegalArgumentException(
                    String.format("Member with email %s doesn't exists", assigneeEmail.get(0)));
        mailService.send(assigneeEmail, Mail.builder().subject(
                "You were assignee to a new task " + newTask.getName() + " !").body(
                "Your new task: " + newTask.getDescription() +
                        "\nCongratulations!").build());
    }

    @Override
    public void taskUpdateMailNotification(Task updatedTask, String comment, User author) {
        List<String> assigneeEmail = new ArrayList<>();
        List<String> taskCreatorEmail = new ArrayList<>();
        assigneeEmail.add(updatedTask.getAssignee().getEmail());
        taskCreatorEmail.add(updatedTask.getReporter().getEmail());
        if (!userService.existsByEmail(assigneeEmail.get(0)))
            throw new IllegalArgumentException(
                    String.format("Member with email %s doesn't exists", assigneeEmail.get(0)));
        if (!userService.existsByEmail(taskCreatorEmail.get(0)))
            throw new IllegalArgumentException(
                    String.format("sMember with email %s doesn't exists", taskCreatorEmail.get(0)));

        mailService.send(assigneeEmail, Mail.builder().subject(
                "Your task " + updatedTask.getName() + "  was updated!").body(
                "Comment: " + comment +
                        "\nby user: " + author.getName() +
                        "\nYour updated task: " + updatedTask.getDescription() +
                        "\nDue date: " + updatedTask.getDueDate() +
                        "\nModification date: " + updatedTask.getModificationDate() +
                        "\nReporter: " + updatedTask.getReporter().getName() +
                        "\nAssignee: " + updatedTask.getAssignee().getName() +
                        "\nProject: " + updatedTask.getProject().getName() +
                        "\nStatus: " + updatedTask.getStatus() +
                        "\nPriority: " + updatedTask.getPriority()
        ).build());
        mailService.send(taskCreatorEmail, Mail.builder().subject(
                "Your task " + updatedTask.getName() + "  was updated!").body(
                "Comment: " + comment +
                        "\nby user: " + author.getName() +
                        "\nYour updated task: " + updatedTask.getDescription() +
                        "\nDue date: " + updatedTask.getDueDate() +
                        "\nModification date: " + updatedTask.getModificationDate() +
                        "\nReporter: " + updatedTask.getReporter().getName() +
                        "\nAssignee: " + updatedTask.getAssignee().getName() +
                        "\nProject: " + updatedTask.getProject().getName() +
                        "\nStatus: " + updatedTask.getStatus() +
                        "\nPriority: " + updatedTask.getPriority()
        ).build());
    }
}
