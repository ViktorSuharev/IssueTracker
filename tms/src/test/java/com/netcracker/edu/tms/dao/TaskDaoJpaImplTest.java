package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest // configures an in-memory embedded database, a JdbcTemplate, and Spring Data JDBC repositories
@Import(TaskDaoJpaImpl.class)


public class TaskDaoJpaImplTest {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * task_id | task_name | task_description  | creation_date | due_date | modification_date | reporterId | assigneeId | projectId | task_status | task_priority
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------
     * 1           task1           desc1               -        2020-05-03           -             100           100          150          -            MINOR
     * 2           task2           desc2               -        2019-12-05           -             200           500          150        CLOSED         MAJOR
     * 3           task3           desc3               -        2021-10-01           -             100           200          150       RESOLVED       CRITICAL
     * 4           task4           desc4               -        2019-07-29           -             400           500          250      IN_PROGRESS     BLOCKER
     * 5           task1           desc5               -        2020-05-03           -             100           500          450        CLOSED        BLOCKER
     */

    @Test
    public void testGetTaskByIdExist() {
        Date dueDate = new Date(1564392911000L);
        Task expected = new Task(
                null, "task4", "desc4", null, dueDate,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250), Status.IN_PROGRES, Priority.BLOCKER);
        BigInteger taskId = (BigInteger) entityManager.persistAndGetId(expected);

        expected.setTaskId(taskId);
        Task actual = taskDao.getTaskById(taskId);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetTaskByIdNotExist() {
        Date dueDate = new Date(1564392911000L);
        Task expected = new Task(null, "task4", "desc4", null, dueDate, null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250), Status.IN_PROGRES, Priority.BLOCKER);//(null, ...);
        BigInteger taskId = (BigInteger) entityManager.persistAndGetId(expected);

        expected.setTaskId(taskId);
        Task actual = taskDao.getTaskById(BigInteger.valueOf(2L + taskId.intValue()));
        Assert.assertNull(actual);
    }

    @Test
    public void testAddTask() {
        Date dueDate = new Date(1588498305000L);
        Task addedTask = new Task(
                null, "task1", "desc1", null, dueDate,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);
        Assert.assertNull(entityManager.getId(addedTask));
        taskDao.addTask(addedTask);
        Assert.assertNotNull(entityManager.getId(addedTask));
    }

    @Test
    public void testDeleteTaskByIdExist() {
        Date dueDate = new Date(1575538511000L);
        Task deletedTask = new Task(
                null, "task2", "desc2", null, dueDate,
                null, BigInteger.valueOf(200), BigInteger.valueOf(500), BigInteger.valueOf(150), Status.CLOSED, Priority.MAJOR);
        BigInteger taskId = (BigInteger) entityManager.persistAndGetId(deletedTask);
        Assert.assertNotNull(entityManager.find(Task.class, taskId));
        taskDao.deleteTask(deletedTask);
        Assert.assertNull(entityManager.find(Task.class, taskId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTaskByIdNotExist() {
        Date dueDate = new Date(1575538511000L);
        Task deletedTask = new Task(BigInteger.valueOf(100L), "task2", "desc2", null, dueDate,
                null, BigInteger.valueOf(200), BigInteger.valueOf(500), BigInteger.valueOf(150), Status.CLOSED, Priority.MAJOR);
        Assert.assertNull(entityManager.find(Task.class, deletedTask.getTaskId()));
        boolean actual = taskDao.deleteTask(deletedTask);
        Assert.assertNull(entityManager.find(Task.class, deletedTask.getTaskId()));
        Assert.assertFalse(actual);
    }

    @Test
    public void testUpdateExistedTask() {
        Date dueDate1 = new Date(1588498511000L);
        Task updatableTask = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);
        updatableTask.setTaskId(null);
        BigInteger taskId = (BigInteger) entityManager.persistAndGetId(updatableTask);
        Assert.assertNotNull(entityManager.find(Task.class, taskId));

        Date dueDate3 = new Date(1633080911000L);
        Task expected = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);
        expected.setTaskId(taskId);

        taskDao.updateTask(expected);

        Assert.assertEquals(expected, entityManager.find(Task.class, taskId));

    }

    @Test
    public void testUpdateNotExistedTask() {
        Date dueDate1 = new Date(1588498511000L);
        Task updatableTask = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);
        Assert.assertNull(entityManager.find(Task.class, updatableTask.getTaskId()));
        Assert.assertFalse(taskDao.updateTask(updatableTask));
    }

    @Test
    public void testGetTaskByName() {
        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);

        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByName("task1").size());
        for (Task task : tasks) {
            task.setTaskId(null);
            entityManager.persist(task);
        }
        Assert.assertEquals(2, taskDao.getTaskByName("task1").size());
        Task[] expected = {tasks.get(0), tasks.get(4)};

        Assert.assertArrayEquals(taskDao.getTaskByName("task1").toArray(), expected);
    }


    @Test
    public void testGetTaskByReporter() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByReporter(BigInteger.valueOf(100L)).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(0), tasks.get(2), tasks.get(4)};

        Assert.assertEquals(3, taskDao.getTaskByReporter(BigInteger.valueOf(100L)).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByReporter(BigInteger.valueOf(100L)).toArray());
    }

    @Test
    public void testGetTaskByAssignee() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByAssignee(BigInteger.valueOf(500L)).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(1), tasks.get(3), tasks.get(4)};

        Assert.assertEquals(3, taskDao.getTaskByAssignee(BigInteger.valueOf(500L)).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByAssignee(BigInteger.valueOf(500L)).toArray());
    }

    @Test
    public void testGetTaskByProject() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByProject(BigInteger.valueOf(150L)).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(0), tasks.get(1), tasks.get(2)};

        Assert.assertEquals(3, taskDao.getTaskByProject(BigInteger.valueOf(150L)).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByProject(BigInteger.valueOf(150L)).toArray());
    }

    @Test
    public void testGetTaskByTaskStatus() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByStatus(Status.CLOSED).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(1), tasks.get(4)};

        Assert.assertEquals(2, taskDao.getTaskByStatus(Status.CLOSED).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByStatus(Status.CLOSED).toArray());
    }

    @Test
    public void testGetTaskByTaskPriority() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByPriority(Priority.BLOCKER).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(3), tasks.get(4)};

        Assert.assertEquals(2, taskDao.getTaskByPriority(Priority.BLOCKER).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByPriority(Priority.BLOCKER).toArray());
    }

    @Test
    public void testGetTaskByCreationDate() {

        Date dueDate1 = new Date(1588498511000L);
        Task task1 = new Task(BigInteger.valueOf(1L), "task1", "desc1", null, dueDate1,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(100L), BigInteger.valueOf(150), null, Priority.MINOR);

        Date dueDate2 = new Date(1575538511000L);
        Task task2 = new Task(BigInteger.valueOf(2L), "task2", "desc2", null, dueDate2,
                null, BigInteger.valueOf(200L), BigInteger.valueOf(500L), BigInteger.valueOf(150L), Status.CLOSED, Priority.MAJOR);

        Date dueDate3 = new Date(1633080911000L);
        Task task3 = new Task(BigInteger.valueOf(3L), "task3", "desc3", null, dueDate3,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(200L), BigInteger.valueOf(150), Status.RESOLVED, Priority.CRITICAL);

        Date dueDate4 = new Date(1564392911000L);
        Task task4 = new Task(BigInteger.valueOf(4L), "task4", "desc4", null, dueDate4,
                null, BigInteger.valueOf(400L), BigInteger.valueOf(500L), BigInteger.valueOf(250L), Status.IN_PROGRES, Priority.BLOCKER);

        Date dueDate5 = new Date(1588498511000L);
        Task task5 = new Task(BigInteger.valueOf(5L), "task1", "desc5", null, dueDate5,
                null, BigInteger.valueOf(100L), BigInteger.valueOf(500L), BigInteger.valueOf(450L), Status.CLOSED, Priority.BLOCKER);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4, task5);

        Assert.assertEquals(0, taskDao.getTaskByCreationDate(new Date(System.currentTimeMillis())).size());
        for (Task task : tasks) {
            task.setTaskId(null);
            BigInteger id = (BigInteger) entityManager.persistAndGetId(task);
            System.out.println(id);
        }
        Task[] expected = {tasks.get(0), tasks.get(1), tasks.get(2), tasks.get(3), tasks.get(4)};

        Assert.assertEquals(5, taskDao.getTaskByCreationDate(new Date(System.currentTimeMillis())).size());
        Assert.assertArrayEquals(expected, taskDao.getTaskByCreationDate(new Date(System.currentTimeMillis())).toArray());
    }


}
