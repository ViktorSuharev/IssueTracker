package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Project;

import java.math.BigInteger;
import java.util.List;

public interface ProjectService {

    /**
     * Get project by specified id
     *
     * @param projectId
     * @return {@link Project} or null
     */
    Project getProjectById(BigInteger projectId);

    /**
     * Add new project to database
     *
     * @param newProject
     * @return true or false depending on operation result
     */
    boolean addProject(Project newProject);

    /**
     * Update existent project
     *
     * @param project
     * @param oldProjectId
     * @return true or false depending on operation result
     */
    boolean updateProject(Project project, BigInteger oldProjectId);

    /**
     * Delete project
     *
     * @param projectId
     * @return true or false depending on operation result
     */
    boolean deleteProject(BigInteger projectId);


    /**
     *
     */
    List<Project> findProjectsByCreatorId(BigInteger creatorId);

    /**
     * Select all projects from DB
     *
     * @return list of {@link Project} or empty list
     */
    List<Project> getAllProjects();

    /**
     *
     */
    Project getProjectsByName(String name);

    /**
     *
     */
    List<Project> getUsersProjects(BigInteger userId);



}