package com.netcracker.edu.tms.project.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectTeam;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.UserWithPassword;

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
    Project getProjectByName(String name);

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
     * @param updatedProjectId
     * @return true or false depending on operation result
     */
    boolean updateProject(Project project, BigInteger updatedProjectId);

    /**
     * delete project from database
     *
     * @param projectId
     * @return true or false depending on operation result
     */

    boolean deleteProject(BigInteger projectId);

    /**
     * Select list of projects from DB by specified creatorId
     *
     * @param creatorId
     * @return list of {@link Project} or empty list
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
    List<Project> getProjectsByUserId(BigInteger userId);

    /**
     *
     */
    List<Task> getTasksByUserId(BigInteger userId);

    /**
     *
     */
    List<UserWithPassword> getTeamByProjectId(BigInteger projectId);

    /**
     *
     */
    boolean addUsersToProjects(ProjectTeam toAdd);

    /**
     *
     */
    boolean deleteUserFromTeam(UserWithPassword userWithPasswordToDelete, BigInteger projectId);

}