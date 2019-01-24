package com.netcracker.edu.tms.dao;


import com.netcracker.edu.tms.model.Project;

import java.math.BigInteger;
import java.util.List;


public interface ProjectDao {


    /**
     * Select project from DB by specified id
     *
     * @param projectId Project Id to return entire project
     * @return {@link Project} or null
     */
    Project getProjectById(BigInteger projectId);

    /**
     * Select project by name
     *
     * @param name
     * @return {@link Project} or null
     */
    List<Project> getProjectByName(String name);

    /**
     * Add new project to database
     *
     * @param project
     * @return true or false depending on operation result
     */
    boolean addProject(Project project);

    /**
     * update existent project in database
     *
     * @param project
     * @param oldProject
     * @return true or false depending on operation result
     */
    boolean updateProject(Project project,Project oldProject);

    /**
     * delete project from database
     *
     * @param project
     * @return true or false depending on operation result
     */

    boolean deleteProject(Project project);

    /**
     * Select list of projects from DB by specified creator_id
     *
     * @param creator_id
     * @return list of {@link Project} or empty list
     */
    List<Project> getProjectsByCreatorId(String creator_id);



    /**
     * Select all projects from DB
     *
     * @return list of {@link Project} or empty list
     */
    List<Project> getAllProjects();

    /**
     * Select project's team
     * @param project_id
     * @return List<{@link User}> team
     *//*
    List<User> getProjectsTeam(BigInteger projectId);*/

    /**
     * Select project's tasks
     * @param project_id
     * @return List<{@link Task}> tasks
     *//*
    List<Task> getProjectsTasks(BigInteger projectId);*/


}