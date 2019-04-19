package com.netcracker.edu.tms.project.service;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.project.model.ProjectTeam;
import com.netcracker.edu.tms.project.model.ProjectWithCreator;
import com.netcracker.edu.tms.project.repository.ProjectDao;
import com.netcracker.edu.tms.mail.model.*;
import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private UserService userService;
    private ProjectDao projectDao;
    private MailService mailService;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao, MailService mailService, UserService userService) {
        this.projectDao = projectDao;
        this.mailService = mailService;
        this.userService = userService;
    }

    @Override
    public Project getProjectById(BigInteger projectId) {
        return projectDao.getProjectById(projectId);
    }

    @Override
    @Transactional
    public boolean addProject(Project newProject) {
        return projectDao.addProject(newProject);
    }

    @Override
    @Transactional
    public boolean updateProject(Project project, BigInteger oldProjectId) {
        return projectDao.updateProject(project, oldProjectId);
    }

    @Override
    @Transactional
    public boolean deleteProject(BigInteger projectId) {
        return projectDao.deleteProject(projectId);
    }

    @Override
    public List<Project> findProjectsByCreatorId(BigInteger creatorId) {
        return projectDao.findProjectsByCreatorId(creatorId);
    }

    @Override
    public List<ProjectWithCreator> getAllProjectsWithCreators() {
        List<Project> projects = projectDao.getAllProjects();
        List<UserWithPassword> creators = new ArrayList<>();

        for(Project project: projects)
            creators.add(userService.getUserByID(project.getCreatorId()));

        List<ProjectWithCreator> result = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++)
            result.add(new ProjectWithCreator(
                    projects.get(i),
                    User.of(creators.get(i))));
        return result;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    public Project getProjectByName(String name) {
        return projectDao.getProjectByName(name);
    }

    @Override
    public List<Project> getProjectsByUserId(BigInteger userId) {
        return projectDao.getProjectsByUserId(userId);
    }

    @Override
    @Transactional
    public boolean setProjectsTeam(BigInteger projectId, List<ProjectMember> team) {

        for (ProjectMember member: team){
            UserWithPassword userWithPassword = userService.getUserByEmail(member.getEmail());

            projectDao.addUsersToProjects(
                    new ProjectTeam(
                            projectId,
                            userWithPassword.getId(),
                            member.getRole()));
        }

        return true;
    }

    @Override
    public List<Task> getTasksByUserId(BigInteger userId) {
        return projectDao.getTasksByUserId(userId);
    }

    @Override
    public List<UserWithPassword> getTeamByProjectId(BigInteger projectId) {
        return projectDao.getTeamByProjectId(projectId);
    }

    @Override
    @Transactional
    public boolean deleteUserFromTeam(UserWithPassword userWithPasswordToDelete, BigInteger projectId) {
        return projectDao.deleteUserFromTeam(userWithPasswordToDelete, projectId);
    }

    @Override
    public void sendInvitationToNewProject(Project project, List<ProjectMember> team) {
        List<String> addedUsersAddresses = new ArrayList<>();

        for (ProjectMember member: team) {
            if (!userService.existsByEmail(member.getEmail()))
                throw new IllegalArgumentException(
                        String.format("Member with email %s doesn't exists", member.getEmail()));
            addedUsersAddresses.add(member.getEmail());
        }

        mailService.send(addedUsersAddresses, Mail.builder().subject(
                "You were invited in new project " + project.getName() + " over MailSenderImpl!").body(
                "Congratulations!").build());
    }
}