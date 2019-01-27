package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

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
        if(ret.isEmpty()){
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
            LOGGER.debug("No such Project in deleteProject appeared");
            return false;
        }
    }

    @Override
    public List<Project> findProjectsByCreatorId(BigInteger creatorId) {
        if (creatorId.compareTo(BigInteger.ZERO) <= 0) {
            return Collections.emptyList();
        }
        Query query = entityManager.createQuery(QueryConsts.SELECT_PROJECTS_BY_CREATOR_ID);
        query.setParameter("creator_id", creatorId);
        List<Project> ret = query.getResultList();
        LOGGER.debug("findProjectsByCreatorId called with {}", creatorId);
        return ret;
    }

    @Override
    public List<Project> getAllProjects() {
        Query query = entityManager.createQuery(QueryConsts.SELECT_ALL);
        List<Project> ret = query.getResultList();
        LOGGER.debug("getAllProjects called");
        return ret;
    }

    // List<User> getProjectsTeam(BigInteger projectId);
    //  List<Task> getProjectsTasks(BigInteger projectId);
}
