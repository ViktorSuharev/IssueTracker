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
        LOGGER.debug("getEmployeeById called with {}", projectId);
        return entityManager.find(Project.class, projectId);
    }

    @Override
    public List<Project> getProjectByName(String name) {
        try {
            Query query = entityManager.createQuery(QueryConsts.SELECT_WITH_NAME);
            query.setParameter("surname", name);
            List<Project> ret = query.getResultList();

            LOGGER.info("getEmployeesBySurname called with \"" + name + "\"");
            return ret;
        } catch (IllegalArgumentException e) {
            LOGGER.warn("IllegalArgumentException in getEmployeesBySurname appeared", e);
            throw e;
        }
    }

    @Override
    public boolean addProject(Project project) {

        entityManager.persist(project);
        LOGGER.debug("addEmployee called with {}", project.toString());
        return true;


    }

    @Override
    public boolean updateProject(Project project, Project oldProject) {

        if (entityManager.contains(oldProject)) {
            entityManager.merge(project);
            LOGGER.debug("updateProject called with {}", project.toString());

            return true;
        } else {
            LOGGER.debug("No such Project with ID= {} in updateProject appeared", oldProject.getId());
            return false;
        }


    }

    @Override
    public boolean deleteProject(Project project) {

        BigInteger identifier = project.getId();

        Project same = entityManager.find(Project.class, identifier);

        if (same.equals(project)) {
            entityManager.remove(same);
            LOGGER.debug("deleteProject called with {}", project.toString());
            return true;
        } else {
            LOGGER.debug("No such Employee in deleteEmpoyee appeared");
            return false;
        }


    }

    @Override
    public List<Project> findProjectsByCreatorId(BigInteger creator_id) {
            if(creator_id.equals(BigInteger.valueOf(-1))){
                return Collections.emptyList();
            }
            Query query = entityManager.createQuery(QueryConsts.SELECT_PROJECTS_BY_CREATOR_ID);
            query.setParameter("creator_id", creator_id);
            List<Project> ret = query.getResultList();

            LOGGER.debug("findProjectsByCreatorId called with {}", creator_id);
            return ret;

    }

    @Override
    public List<Project> getAllProjects() {
        Query query = entityManager.createQuery(QueryConsts.SELECT_ALL);
        List<Project> ret = query.getResultList();
        LOGGER.debug("getAllEmployees called");
        return ret;
    }

    // List<User> getProjectsTeam(BigInteger projectId);
    //  List<Task> getProjectsTasks(BigInteger projectId);
}
