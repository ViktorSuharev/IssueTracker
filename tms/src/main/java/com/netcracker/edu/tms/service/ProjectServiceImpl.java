package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.dao.ProjectDao;
import com.netcracker.edu.tms.model.Mail;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.Task;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.model.UsersToProjects;
import com.netcracker.edu.tms.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectDao projectDao;
    private MailService mailService;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao, MailService mailService) {
        this.projectDao = projectDao;
        this.mailService = mailService;
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
    public boolean setProjectsTeam(List<User> addedUsers, BigInteger id) {
        for (User toAdd : addedUsers) {
            projectDao.addUsersToProjects(new UsersToProjects(null, toAdd.getId(), id));
        }
        return true;
    }

    @Override
    public List<Task> getTasksByUserId(BigInteger userId) {
        return projectDao.getTasksByUserId(userId);
    }

    @Override
    public List<User> getTeamByProjectId(BigInteger projectId) {
        return projectDao.getTeamByProjectId(projectId);
    }

    @Override
    @Transactional
    public boolean deleteUserFromTeam(User userToDelete, BigInteger projectId) {
        return projectDao.deleteUserFromTeam(userToDelete, projectId);
    }

    @Override
    public void sendInvitationToNewProject(List<User> addedUsers, Project newProject) {
        List<String> addedUsersAddresses = new ArrayList<>();
        for (User user : addedUsers) {
            /*addedUsersAddresses.add(user.getEmail());*/
            addedUsersAddresses.add("dmitrybobryakov@gmail.com");
        }
            mailService.send(addedUsersAddresses, Mail.builder().subject(
                "You were invited in new project " + newProject.getName() + " over MailSenderImpl!").body(
                "Congratulations!").build());
    }
}