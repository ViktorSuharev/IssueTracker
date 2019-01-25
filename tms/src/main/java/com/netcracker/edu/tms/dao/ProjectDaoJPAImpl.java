package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.*;

@Repository
public class ProjectDaoJPAImpl implements ProjectDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project getProjectById(BigInteger projectId) {
        try{
            LOGGER.info("getEmployeeById called with \""+projectId+"\"");
            return entityManager.find(Project.class, projectId);
        }catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeeById appeared",e);
            throw e;
        }
    }

    @Override
    public List<Project> getProjectByName(String name) {
        try {
            Query query = entityManager.createQuery(QueryConsts.SELECT_WITH_NAME);
            query.setParameter("surname", name);
            List<Project> ret = query.getResultList();

            LOGGER.info("getEmployeesBySurname called with \""+name+"\"");
            return ret;
        }catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeesBySurname appeared",e);
            throw e;
        }
    }

    @Override
    public boolean addProject(Project project) {
        try{
            entityManager.persist(project);
            LOGGER.info("addEmployee called with \""+project+"\"");
            return true;
        } catch (Exception e) {
            LOGGER.warn("Some exception appeared in addEmployee",e);
            throw e;
        }

    }

    @Override
    public boolean updateProject(Project project, Project oldProject) {
        try {
            if(entityManager.contains(oldProject)){
                entityManager.merge(project);
                LOGGER.info("updateProject called with \""+project+"\"");

                return true;
            }else{
                LOGGER.warn("No such Project in updateProject appeared");
                throw new IllegalStateException("No such Project");//Not found entity to update
            }

        } catch (Exception e) {
            LOGGER.warn("IllegalArgumentException in updateProject appeared",e);
            throw e;
        }

    }

    @Override
    public boolean deleteProject(Project project) {
        try {
            BigInteger identifier=project.getId();

            Project same=entityManager.find(Project.class,identifier);
            if(same.equals(project)) {
                entityManager.remove(same);
                LOGGER.info("deleteProject called with \""+project+"\"");
                return true;
            }else{
                LOGGER.warn("No such Employee in deleteEmpoyee appeared");
                return false;
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("IllegalArgumentException in deleteEmployee appeared",e);
            throw e;
        }
        catch (NullPointerException e) {
            LOGGER.warn("NullPointerException in deleteEmployee appeared",e);
            throw e;
        }
    }

    @Override
    public List<Project> getProjectsByCreatorId(String creator_id) {
        try {
            Query query = entityManager.createQuery(QueryConsts.SELECT_PROJECTS_BY_CREATOR_ID);
            query.setParameter("creator_id", creator_id);
            List<Project> ret = query.getResultList();

            LOGGER.info("getEmployeesBySurname called with \""+creator_id+"\"");
            return ret;
        }catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeesBySurname appeared",e);
            throw e;
        }
    }

    @Override
    public List<Project> getAllProjects() {
        Query query = entityManager.createQuery(QueryConsts.SELECT_ALL);
        List<Project> ret = query.getResultList();
        LOGGER.info("getAllEmployees called");
        return ret;
    }

   // List<User> getProjectsTeam(BigInteger projectId);
  //  List<Task> getProjectsTasks(BigInteger projectId);
}
