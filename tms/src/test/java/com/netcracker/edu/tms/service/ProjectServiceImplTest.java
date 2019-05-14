package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.mail.service.mail.MailServiceImpl;
import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.project.model.ProjectRole;
import com.netcracker.edu.tms.project.repository.ProjectRepository;
import com.netcracker.edu.tms.project.repository.ProjectRoleRepository;
import com.netcracker.edu.tms.project.repository.TeamRepository;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.project.service.ProjectServiceImpl;
import com.netcracker.edu.tms.task.service.TaskService;
import com.netcracker.edu.tms.task.service.TaskServiceImpl;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import com.netcracker.edu.tms.user.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;

import org.springframework.dao.DataAccessException;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectServiceImplTest {

    @Mock
    private UserService userService = mock(UserServiceImpl.class);

    @Mock
    private TaskService taskService = mock(TaskServiceImpl.class);

    @Mock
    private MailService mailService = mock(MailServiceImpl.class);

    @Mock
    private TeamRepository teamRepository = mock(TeamRepository.class);

    @Mock
    private ProjectRepository projectRepository = mock(ProjectRepository.class);

    @Mock
    private ProjectRoleRepository projectRoleRepository = mock(ProjectRoleRepository.class);

    private ProjectService projectService =
            new ProjectServiceImpl(userService, mailService, teamRepository, projectRepository, projectRoleRepository, taskService);

    @Test
    public void getProjectById() {
        User user = new User();
        Project stubProject = new Project(BigInteger.ONE, user, "");
        when(projectRepository.findById(BigInteger.ONE)).thenReturn(Optional.of(stubProject));
        Project stub = projectRepository.findById(BigInteger.ONE).get();
        Project project = projectService.getProjectById(BigInteger.ONE);

        Assert.assertEquals(stub, project);
    }

    @Test(expected = NullPointerException.class)
    public void getProjectByIdNonExistent() {
        when(projectRepository.findById(BigInteger.ONE)).thenReturn(null);
        projectService.getProjectById(BigInteger.ONE);
    }

    @Test
    public void addProject() {
        User user = new User();
        Project inserted = new Project(BigInteger.valueOf(2L), user, "");
        when(projectRepository.save(inserted)).thenReturn(inserted);

        Assert.assertEquals(projectService.addProject(inserted), inserted);
    }

    @Test(expected = DataAccessException.class)
    public void addProjectExistent() {
        User user = new User();
        Project inserted = new Project(BigInteger.valueOf(2L), user, "");
        when(projectRepository.save(inserted)).thenThrow(new DataAccessException("...") {
        });
        projectService.addProject(inserted);
    }

    @Test
    public void updateProject() {
        User user = new User();
        Project stubToUpdate = new Project(BigInteger.valueOf(2L), user, "");
        Project updatedStub = new Project(BigInteger.valueOf(4L), user, "");
        when(projectRepository.save(stubToUpdate.clone(updatedStub))).thenReturn(stubToUpdate.clone(updatedStub));
        Assert.assertEquals(stubToUpdate.clone(updatedStub), projectService.updateProject(stubToUpdate, updatedStub));
    }

    @Test
    public void deleteProject() {
        User user = new User();
        Project stub = new Project(BigInteger.valueOf(2L), user, "");
        doNothing().when(teamRepository).deleteAllByProject(stub);
        doNothing().when(taskService).deleteAllTasksByProject(stub);
        doNothing().when(projectRepository).deleteById(stub.getId());
        Assert.assertTrue(projectService.deleteProject(stub));
    }

    @Test
    public void findProjectsByCreatorId() {
        User stubCreator = new User();
        Project stubProject1 = new Project(BigInteger.valueOf(1L), stubCreator, "");
        Project stubProject2 = new Project(BigInteger.valueOf(2L), stubCreator, "");
        Project stubProject3 = new Project(BigInteger.valueOf(3L), stubCreator, "");
        Project stubProject4 = new Project(BigInteger.valueOf(4L), stubCreator, "");
        Project stubProject5 = new Project(BigInteger.valueOf(5L), stubCreator, "");
        Project[] stubArray = {stubProject1, stubProject2, stubProject3, stubProject4, stubProject5};
        List<Project> stubList = Arrays.asList(stubArray);
        when(projectRepository.findAllByCreator(stubCreator)).thenReturn(stubList);

        Assert.assertEquals(stubList, projectService.getProjectsByCreator(stubCreator));
    }

    @Test
    public void getAllProjects() {
        User stubCreator = new User();
        Project stubProject1 = new Project(BigInteger.valueOf(1L), stubCreator, "");
        Project stubProject2 = new Project(BigInteger.valueOf(2L), stubCreator, "");
        Project stubProject3 = new Project(BigInteger.valueOf(3L), stubCreator, "");
        Project stubProject4 = new Project(BigInteger.valueOf(4L), stubCreator, "");
        Project stubProject5 = new Project(BigInteger.valueOf(5L), stubCreator, "");
        Project[] stubArray = {stubProject1, stubProject2, stubProject3, stubProject4, stubProject5};
        List<Project> stubList = Arrays.asList(stubArray);
        when(projectRepository.findAll()).thenReturn(stubList);

        Assert.assertEquals(stubList, projectService.getAllProjects());
    }

    @Test
    public void getProjectsOfUser() {
        User stubUser = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubUser, "");
        ProjectMember pm1 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember pm2 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember pm3 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember[] stubArray = {pm1, pm2, pm3};
        List<ProjectMember> stubList = Arrays.asList(stubArray);
        when(teamRepository.findAllByUser(stubUser)).thenReturn(stubList);

        Assert.assertEquals(
                StreamSupport.stream(stubList.spliterator(), false)
                        .map(m -> m.getProject())
                        .collect(Collectors.toList()),
                projectService.getProjectsOfUser(stubUser));
    }

    @Test
    public void setProjectsTeam() {
        Project project = new Project();
        Iterable<ProjectMember> team = new ArrayList<>();
        when(teamRepository.saveAll(any())).thenReturn(null);

        Assert.assertFalse(projectService.setProjectsTeam(project, team));
    }

    @Test
    public void setProjectsTeamNothingToSave() {
        Project project = new Project();
        Iterable<ProjectMember> team = new ArrayList<>();
        when(teamRepository.saveAll(any())).thenReturn(team);

        Assert.assertTrue(projectService.setProjectsTeam(project, team));
    }

    @Test
    public void getTeamByProject() {
        User stubCreator = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubCreator, "");
        ProjectMember pm1 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm2 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm3 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember[] stubArray = {pm1, pm2, pm3};
        List<ProjectMember> stubList = Arrays.asList(stubArray);
        when(teamRepository.findAllByProject(stubProject)).thenReturn(stubList);

        Assert.assertEquals(stubList, projectService.getTeamByProject(stubProject));
    }

    @Test
    public void deleteProjectMember() {
        User stubCreator = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubCreator, "");
        ProjectMember pm = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        doNothing().when(teamRepository).deleteByProjectAndUser(pm.getProject(), pm.getUser());
    }

    @Test
    public void deleteTeam() {
        User stubCreator = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubCreator, "");
        doNothing().when(teamRepository).deleteAllByProject(stubProject);

        Assert.assertTrue(projectService.deleteTeam(stubProject));
    }

    @Test
    public void sendInvitationToNewProject() {
        User stubCreator = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubCreator, "");
        ProjectMember pm1 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm2 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm3 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember[] stubArray = {pm1, pm2, pm3};
        List<ProjectMember> stubList = Arrays.asList(stubArray);
        when(teamRepository.findAllByProject(stubProject)).thenReturn(stubList);
        when(userService.existsByEmail(any())).thenReturn(true);
        doNothing().when(mailService).send(any(), any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendInvitationToNewProjectEmailNonExistent() {
        User stubCreator = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubCreator, "");
        ProjectMember pm1 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm2 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember pm3 = new ProjectMember(BigInteger.valueOf(1L), stubProject, new User(), new ProjectRole());
        ProjectMember[] stubArray = {pm1, pm2, pm3};
        List<ProjectMember> stubList = Arrays.asList(stubArray);
        when(teamRepository.findAllByProject(stubProject)).thenReturn(stubList);
        when(userService.existsByEmail(any())).thenReturn(false);
        projectService.sendInvitationToNewProject(stubProject);
    }

    @Test
    public void createRoleNonExistent() {
        String stubName = "stub";
        ProjectRole stubRole = new ProjectRole(stubName);
        when(projectRoleRepository.findByName(stubName)).thenReturn(null);
        when(projectRoleRepository.save(stubRole)).thenReturn(stubRole);

        Assert.assertEquals(stubRole, projectService.createRoleIfNotExists(stubName));
    }

    @Test
    public void createRoleExistent() {
        String stubName = "stub";
        ProjectRole stubRole = new ProjectRole(stubName);
        when(projectRoleRepository.findByName(stubName)).thenReturn(stubRole);

        Assert.assertEquals(stubRole, projectService.createRoleIfNotExists(stubName));
    }

    @Test
    public void getProjectsWithUser() {
        User stubUser = new User();
        Project stubProject = new Project(BigInteger.valueOf(1L), stubUser, "");
        ProjectMember pm1 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember pm2 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember pm3 = new ProjectMember(BigInteger.valueOf(1L), stubProject, stubUser, new ProjectRole());
        ProjectMember[] stubArray = {pm1, pm2, pm3};
        List<ProjectMember> stubList = Arrays.asList(stubArray);
        when(teamRepository.findAllByUser(stubUser)).thenReturn(stubList);

        Assert.assertEquals(stubList, projectService.getProjectsWithUser(stubUser));

    }
}