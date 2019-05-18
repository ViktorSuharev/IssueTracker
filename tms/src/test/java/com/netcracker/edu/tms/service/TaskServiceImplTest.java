package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.History;
import com.netcracker.edu.tms.task.model.Priority;
import com.netcracker.edu.tms.task.model.Status;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.task.repository.HistoryRepository;
import com.netcracker.edu.tms.task.repository.TaskDao;
import com.netcracker.edu.tms.task.repository.TaskDaoJpaImpl;
import com.netcracker.edu.tms.task.repository.TaskRepository;
import com.netcracker.edu.tms.task.service.TaskService;
import com.netcracker.edu.tms.task.service.TaskServiceImpl;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    @Mock
    private TaskDao taskDao = mock(TaskDaoJpaImpl.class);

    @Mock
    private TaskRepository taskRepository = mock(TaskRepository.class);

    @Mock
    private HistoryRepository historyRepository = mock(HistoryRepository.class);

    @Mock
    private MailService mailService = mock(MailService.class);

    @Mock
    private UserService userService = mock(UserService.class);

    private final TaskService taskService = new TaskServiceImpl(taskDao, taskRepository, historyRepository, mailService, userService);

    @Test
    public void getTaskById() {
        Task stubTask = new Task();
        BigInteger stubId = BigInteger.ONE;
        when(taskDao.getTaskById(stubId)).thenReturn(stubTask);

        Assert.assertEquals(stubTask, taskService.getTaskById(stubId));
    }

    @Test
    public void getTaskByName() {
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        String stubName = "stubName";
        when(taskDao.getTaskByName(stubName)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByName(stubName));
    }

    @Test
    public void addTaskNonExistent() {
        Task stubTask = new Task();
        when(taskRepository.existsByProjectAndName(stubTask.getProject(), stubTask.getName())).thenReturn(false);
        when(taskDao.addTask(stubTask)).thenReturn(true);

        Assert.assertTrue(taskService.addTask(stubTask));
    }

    @Test
    public void addTaskExistent() {
        Task stubTask = new Task();
        when(taskRepository.existsByProjectAndName(stubTask.getProject(), stubTask.getName())).thenReturn(true);
        when(taskDao.addTask(stubTask)).thenReturn(false);

        Assert.assertFalse(taskService.addTask(stubTask));
    }


    @Test
    public void updateTaskExistent() {
        Task stubTask = new Task();
        String stubComment = "stubComment";
        User stubAuthor = new User();

        when(taskRepository.findById(stubTask.getId())).thenReturn(Optional.of(stubTask));
        when(historyRepository.save(any())).thenReturn(new History());
        when(taskDao.updateTask(stubTask)).thenReturn(true);

        Assert.assertTrue(taskService.updateTask(stubTask, stubComment, stubAuthor));
    }

    @Test(expected = NullPointerException.class)
    public void updateTaskNonExistent() {
        Task stubTask = new Task();
        String stubComment = "stubComment";
        User stubAuthor = new User();

        when(taskRepository.findById(stubTask.getId())).thenReturn(null);
        taskService.updateTask(stubTask, stubComment, stubAuthor);

    }

    @Test
    public void deleteTaskExistent() {
        Task stubTask = new Task();

        when(taskDao.getTaskById(stubTask.getId())).thenReturn(stubTask);
        when(taskDao.deleteTask(stubTask)).thenReturn(true);

        Assert.assertTrue(taskService.deleteTask(stubTask));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteTaskNonExistent() {
        Task stubTask = new Task();

        when(taskDao.getTaskById(stubTask.getId())).thenReturn(null);
        taskService.deleteTask(stubTask);
    }

    @Test
    public void getAll() {
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);

        when(taskRepository.findAll()).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getAll());
    }

    @Test
    public void getTaskByReporter() {
        User stubReporter = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskRepository.findAllByReporter(stubReporter)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByReporter(stubReporter));
    }

    @Test
    public void getTaskByAssignee() {
        User stubAssignee = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskRepository.findAllByAssignee(stubAssignee)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByAssignee(stubAssignee));
    }

    @Test
    public void getTaskByCreationDate() throws ParseException {
        String stubDate = "22-10-2018";
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskDao.getTaskByCreationDate(any())).thenReturn(stubList);


        Assert.assertEquals(stubList, taskService.getTaskByCreationDate(stubDate));
    }

    @Test
    public void getTaskByProject() {
        Project stubProject = new Project();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskRepository.findAllByProject((stubProject))).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByProject(stubProject));
    }

    @Test
    public void getTaskByStatus() {
        Status stubStatus = Status.IN_PROGRESS;
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskDao.getTaskByStatus(stubStatus)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByStatus(stubStatus));
    }

    @Test
    public void getTaskByPriority() {
        Priority stubPriority = Priority.MAJOR;
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);
        when(taskDao.getTaskByPriority(stubPriority)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getTaskByPriority(stubPriority));
    }

    @Test
    public void getHistoryByTaskId() {
        BigInteger stubId = BigInteger.ONE;
        History stubHistory1 = new History();
        History stubHistory2 = new History();
        History stubHistory3 = new History();
        History[] stubArray = {stubHistory1, stubHistory2, stubHistory3};
        List<History> stubList = Arrays.asList(stubArray);

        when(historyRepository.findAllByTaskId(stubId)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getHistoryByTaskId(stubId));
    }

    @Test
    public void getActiveTasksByAssignee() {
        User stubAssignee = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray1 = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList1 = Arrays.asList(stubArray1);

        Task stubTask4 = new Task();
        Task stubTask5 = new Task();
        Task stubTask6 = new Task();
        Task[] stubArray2 = {stubTask4, stubTask5, stubTask6};
        List<Task> stubList2 = Arrays.asList(stubArray1);

        when(taskRepository.findAllByAssigneeAndStatus(stubAssignee, Status.NOT_STARTED)).thenReturn(stubList1);
        when(taskRepository.findAllByAssigneeAndStatus(stubAssignee, Status.IN_PROGRESS)).thenReturn(stubList2);

        List<Task> expected = new ArrayList<>();
        expected.addAll(stubList1);
        expected.addAll(stubList2);

        Assert.assertEquals(expected, taskService.getActiveTasksByAssignee(stubAssignee));
    }

    @Test
    public void deleteAllTasksByProject() {
        Project stubProject = new Project();
        doNothing().when(taskRepository).deleteAllByProject(stubProject);
    }

    @Test
    public void getActiveTasksByProject() {
        Project stubProject = new Project();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray1 = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList1 = Arrays.asList(stubArray1);

        Task stubTask4 = new Task();
        Task stubTask5 = new Task();
        Task stubTask6 = new Task();
        Task[] stubArray2 = {stubTask4, stubTask5, stubTask6};
        List<Task> stubList2 = Arrays.asList(stubArray1);

        when(taskRepository.findAllByProjectAndStatus(stubProject, Status.NOT_STARTED)).thenReturn(stubList1);
        when(taskRepository.findAllByProjectAndStatus(stubProject, Status.IN_PROGRESS)).thenReturn(stubList2);

        List<Task> expected = new ArrayList<>();
        expected.addAll(stubList1);
        expected.addAll(stubList2);

        Assert.assertEquals(expected, taskService.getActiveTasksByProject(stubProject));
    }

    @Test
    public void getResolvedTasksByProject() {
        Project stubProject = new Project();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray1 = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList1 = Arrays.asList(stubArray1);

        Task stubTask4 = new Task();
        Task stubTask5 = new Task();
        Task stubTask6 = new Task();
        Task[] stubArray2 = {stubTask4, stubTask5, stubTask6};
        List<Task> stubList2 = Arrays.asList(stubArray1);

        when(taskRepository.findAllByProjectAndStatus(stubProject, Status.NOT_STARTED)).thenReturn(stubList1);
        when(taskRepository.findAllByProjectAndStatus(stubProject, Status.IN_PROGRESS)).thenReturn(stubList2);

        List<Task> expected = new ArrayList<>();
        expected.addAll(stubList1);
        expected.addAll(stubList2);

        Assert.assertEquals(expected, taskService.getActiveTasksByProject(stubProject));
    }

    @Test
    public void getAllActiveTasks() {
        Project stubProject = new Project();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray1 = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList1 = Arrays.asList(stubArray1);

        Task stubTask4 = new Task();
        Task stubTask5 = new Task();
        Task stubTask6 = new Task();
        Task[] stubArray2 = {stubTask4, stubTask5, stubTask6};
        List<Task> stubList2 = Arrays.asList(stubArray1);

        when(taskRepository.findAllByStatus(Status.NOT_STARTED)).thenReturn(stubList1);
        when(taskRepository.findAllByStatus(Status.IN_PROGRESS)).thenReturn(stubList2);

        List<Task> expected = new ArrayList<>();
        expected.addAll(stubList1);
        expected.addAll(stubList2);

        Assert.assertEquals(expected, taskService.getAllActiveTasks());
    }

    @Test
    public void getResolvedTasksByAssignee() {
        User stubAssignee = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);

        when(taskRepository.findAllByAssigneeAndStatus(stubAssignee, Status.RESOLVED)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getResolvedTasksByAssignee(stubAssignee));
    }

    @Test
    public void getResolvedTasksByReporter() {
        User stubReporter = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);

        when(taskRepository.findAllByReporterAndStatus(stubReporter, Status.RESOLVED)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getResolvedTasksByReporter(stubReporter));
    }

    @Test
    public void getClosedTasksByAssignee() {
        User stubAssignee = new User();
        Task stubTask1 = new Task();
        Task stubTask2 = new Task();
        Task stubTask3 = new Task();
        Task[] stubArray = {stubTask1, stubTask2, stubTask3};
        List<Task> stubList = Arrays.asList(stubArray);

        when(taskRepository.findAllByAssigneeAndStatus(stubAssignee, Status.CLOSED)).thenReturn(stubList);

        Assert.assertEquals(stubList, taskService.getClosedTasksByAssignee(stubAssignee));
    }
}
