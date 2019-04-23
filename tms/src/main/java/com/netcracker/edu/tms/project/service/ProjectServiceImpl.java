package com.netcracker.edu.tms.project.service;

import com.netcracker.edu.tms.mail.model.Mail;
import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.project.model.ProjectRole;
import com.netcracker.edu.tms.project.repository.ProjectRepository;
import com.netcracker.edu.tms.project.repository.ProjectRoleRepository;
import com.netcracker.edu.tms.project.repository.TeamRepository;
import com.netcracker.edu.tms.task.service.TaskService;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectServiceImpl implements ProjectService {

    private UserService userService;
    private TaskService taskService;
    private MailService mailService;
    private TeamRepository teamRepository;
    private ProjectRepository projectRepository;
    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    public ProjectServiceImpl(UserService userService, MailService mailService, TeamRepository teamRepository,
                              ProjectRepository projectRepository, ProjectRoleRepository projectRoleRepository,
                              TaskService taskService) {
        this.userService = userService;
        this.mailService = mailService;
        this.taskService = taskService;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.projectRoleRepository = projectRoleRepository;
    }

    @Override
    public Project getProjectById(BigInteger projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    @Transactional
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project updateProject(Project projectToUpdate, Project newProject) {
        Project updated = projectToUpdate.clone(newProject);
        return projectRepository.save(updated);
    }

    @Override
    @Transactional
    public boolean deleteProject(Project project) {
        teamRepository.deleteAllByProject(project);
        projectRepository.deleteById(project.getId());
        taskService.deleteAllTasksByProject(project);

        return true;
    }

    @Override
    public Iterable<Project> getProjectsByCreator(User creator) {
        return projectRepository.findAllByCreator(creator);
    }

    @Override
    public Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectsOfUser(User user) {
        return StreamSupport.stream(teamRepository.findAllByUser(user).spliterator(), false)
                .map(m -> m.getProject())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean setProjectsTeam(Project project, Iterable<ProjectMember> team) {
        List<ProjectMember> res = StreamSupport.stream(team.spliterator(), false)
                .map(e -> new ProjectMember(e.getId(), project, e.getUser(), e.getRole()))
                .collect(Collectors.toList());

        return teamRepository.saveAll(res) != null;
    }

    @Override
    public Iterable<ProjectMember> getTeamByProject(Project project) {
        return teamRepository.findAllByProject(project);
    }

    @Override
    @Transactional
    public void deleteProjectMember(ProjectMember projectMember) {
        teamRepository.deleteByProjectAndUser(projectMember.getProject(), projectMember.getUser());
    }

    @Override
    public boolean deleteTeam(Project project) {
        teamRepository.deleteAllByProject(project);
        return true;
    }

    @Override
    public void sendInvitationToNewProject(Project project) {
        List<String> addedUsersAddresses = new ArrayList<>();
        Iterable<ProjectMember> team = teamRepository.findAllByProject(project);

        for (ProjectMember m : team) {
            if (!userService.existsByEmail(m.getUser().getEmail()))
                throw new IllegalArgumentException(
                        String.format("Member with email %s doesn't exists", m.getUser().getEmail()));
            addedUsersAddresses.add(m.getUser().getEmail());
        }

        mailService.send(addedUsersAddresses, Mail.builder().subject(
                "You were invited in new project " + project.getName() + " over MailSenderImpl!").body(
                "Congratulations!").build());
    }

    @Override
    public ProjectRole createRoleIfNotExists(String name) {
        ProjectRole role = projectRoleRepository.findByName(name);
        return role == null ? projectRoleRepository.save(new ProjectRole(name)) : role;
    }

    @Override
    public Iterable<ProjectMember> getProjectsWithUser(User user) {
        return teamRepository.findAllByUser(user);
    }

}