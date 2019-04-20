package com.netcracker.edu.tms.project.service;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.project.model.ProjectRole;
import com.netcracker.edu.tms.user.model.User;

import java.math.BigInteger;
import java.util.List;

public interface ProjectService {

    Project getProjectById(BigInteger projectId);

    Project addProject(Project project);

    Project updateProject(Project projectToUpdate, Project newProject);

    boolean deleteProject(Project project);

    Iterable<Project> findProjectsByCreator(User creator);

    Iterable<Project> getAllProjects();

    List<Project> getProjectsOfUser(User user);

    boolean setProjectsTeam(Project project, Iterable<ProjectMember> team);

    Iterable<ProjectMember> getTeamByProject(Project project);

    void deleteProjectMember(ProjectMember projectMember);

    boolean deleteTeam(Project project);

    void sendInvitationToNewProject(Project project);

    ProjectRole createRoleIfNotExists(String name);
}