package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.Task;
import com.netcracker.edu.tms.model.User;

import com.netcracker.edu.tms.model.UsersToProjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class ProjectDaoJpaImpl implements ProjectDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project getProjectById(BigInteger projectId) {
        LOGGER.debug("getProjectById called with {}", projectId);
        return entityManager.find(Project.class, projectId);
    }

    @Override
    public Project getProjectByName(String name) {
        Query query = entityManager.createQuery(QueryConsts.SELECT_WITH_NAME);
        query.setParameter("name", name);
        List<Project> ret = query.getResultList();

        LOGGER.info("getProjectByName called with {}", name);
        if (ret.isEmpty()) {
            return null;
        }
        return ret.get(0);
    }

    @Override
    public boolean addProject(Project project) {
        entityManager.persist(project);
        LOGGER.debug("addProject called with {}", project.toString());
        return true;
    }

    @Override
    public boolean updateProject(Project project, BigInteger updatedProjectId) {
        if (entityManager.find(Project.class, updatedProjectId) != null) {
            entityManager.merge(project);
            LOGGER.debug("updateProject called with {}", project.toString());
            return true;
        } else {
            LOGGER.warn("No such Project with ID= {} in updateProject appeared", updatedProjectId);
            return false;
        }
    }

    @Override
    public boolean deleteProject(BigInteger projectId) {
        Project same = entityManager.find(Project.class, projectId);

        if (same != null) {
            entityManager.remove(same);
            LOGGER.debug("deleteProject called with {}", projectId.toString());
            return true;
        } else {
            LOGGER.warn("No such Project with id={} in deleteProject appeared", projectId);
            return false;
        }
    }

    @Override
    public List<Project> findProjectsByCreatorId(BigInteger creatorId) {
        if (BigInteger.ZERO.compareTo(creatorId) >= 0) {
            return Collections.emptyList();
        }
        Query query = entityManager.createQuery(QueryConsts.SELECT_PROJECTS_BY_CREATOR_ID);
        query.setParameter("creatorId", creatorId);
        List<Project> ret = query.getResultList();
        LOGGER.debug("findProjectsByCreatorId called with {}", creatorId);
        return ret;
    }

    @Override
    public List<Project> getAllProjects() {
        Query query = entityManager.createQuery(QueryConsts.SELECT_ALL);
        List<Project> ret = query.getResultList();
        LOGGER.debug("getAllProjectsWithCreators called");
        return ret;
    }

    @Override
    public List<Project> getProjectsByUserId(BigInteger userId) {
        Query query = entityManager.createQuery(QueryConsts.SELECT_USERS_PROJECTS);
        query.setParameter("userId", userId);
        List<Project> ret = query.getResultList();
        LOGGER.debug("getProjectsByUserId called with userId={}", userId);
        return ret;
    }

    @Override
    public List<Task> getTasksByUserId(BigInteger userId) {
        Query query = entityManager.createQuery(QueryConsts.SELECT_USERS_TASKS);
        query.setParameter("userId", userId);
        List<Task> ret = query.getResultList();
        LOGGER.debug("getTasksByUserId called with userId={}", userId);
        return ret;
    }

    @Override
    public List<User> getTeamByProjectId(BigInteger projectId) {
        Query query = entityManager.createQuery(QueryConsts.SELECT_TEAM_FOR_PROJECT_ID);
        query.setParameter("projectId", projectId);
        List<User> ret = query.getResultList();
        LOGGER.debug("getTeamByProjectId called with projectId={}", projectId);
        return ret;
    }

    @Override
    public boolean addUsersToProjects(UsersToProjects toAdd) {
        entityManager.persist(toAdd);
        LOGGER.debug("addUsersToProjects called with entry={}", toAdd.toString());
        return true;
    }

    @Override
    public boolean deleteUserFromTeam(User userToDelete, BigInteger projectId) {

        UsersToProjects same = entityManager.find(UsersToProjects.class, userToDelete.getId());

        if (same != null) {
            entityManager.remove(same);
            LOGGER.debug("deleteUserFromTeam called with {}", userToDelete.getId());
            return true;
        } else {
            LOGGER.warn("No such User with id={} in deleteUserFromTeam appeared", userToDelete.getId());
            return false;
        }
    }
}
