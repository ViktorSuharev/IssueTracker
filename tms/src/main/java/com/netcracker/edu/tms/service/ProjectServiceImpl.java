package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.dao.ProjectDao;
import com.netcracker.edu.tms.model.*;
import com.netcracker.edu.tms.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            User user = userService.getUserByEmail(member.getEmail());

            projectDao.addUsersToProjects(
                    new UsersToProjects(
                            projectId,
                            user.getId(),
                            member.getRole()));
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