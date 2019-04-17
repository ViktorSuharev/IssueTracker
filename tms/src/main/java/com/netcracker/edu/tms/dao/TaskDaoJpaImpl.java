package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public class TaskDaoJpaImpl implements TaskDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Task getTaskById(BigInteger taskId) {
        return entityManager.find(Task.class, taskId);
    }

    @Override
    public List<Task> getTaskByName(String taskName) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "taskName"));
        namesQuery.setParameter("parameter", taskName);
        return namesQuery.getResultList();
    }

    @Override
    public boolean addTask(Task task) {
        entityManager.persist(task);
        return entityManager.find(Task.class, task.getTaskId()) != null;
    }

    @Override
    public boolean updateTask(Task task) {
        if (entityManager.find(Task.class, task.getTaskId()) == null)
            return false;
        else {
            entityManager.merge(task);
            return true;
        }
    }

    @Override
    public boolean deleteTask(Task task) {
        Task deletedTask = entityManager.find(Task.class, task.getTaskId());
        entityManager.remove(deletedTask);
        return entityManager.find(Task.class, task.getTaskId()) == null;
    }

    @Override
    public List<Task> getTaskByReporter(BigInteger reporterId) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "reporterId"));
        namesQuery.setParameter("parameter", reporterId);
        return namesQuery.getResultList();
    }

    @Override
    public List<Task> getTaskByAssignee(BigInteger assigneeId) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "assigneeId"));
        namesQuery.setParameter("parameter", assigneeId);
        return namesQuery.getResultList();
    }

    @Override
    public List<Task> getTaskByCreationDate(Date creationDate) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "creationDate"));
        namesQuery.setParameter("parameter", creationDate);
        return namesQuery.getResultList();
    }

    @Override
    public List<Task> getTaskByProject(BigInteger projectId) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "projectId"));
        namesQuery.setParameter("parameter", projectId);
        return namesQuery.getResultList();
    }

    @Override
    public List<Task> getTaskByStatus(Status taskStatus) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "status"));
        namesQuery.setParameter("parameter", taskStatus);
        return namesQuery.getResultList();
    }

    @Override
    public List<Task> getTaskByPriority(Priority taskPriority) {
        Query namesQuery = entityManager.
                createQuery(String.format(QueryBuilder.selectQuery, "Task", "priority"));
        namesQuery.setParameter("parameter", taskPriority);
        return namesQuery.getResultList();
    }
}
