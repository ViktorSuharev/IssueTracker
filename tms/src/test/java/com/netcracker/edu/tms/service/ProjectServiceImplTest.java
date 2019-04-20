//package com.netcracker.edu.tms.service;
//
//import com.netcracker.edu.tms.mail.service.mail.MailService;
//import com.netcracker.edu.tms.project.model.Project;
//import com.netcracker.edu.tms.project.service.ProjectService;
//import com.netcracker.edu.tms.project.service.ProjectServiceImpl;
//import com.netcracker.edu.tms.task.model.Task;
//import com.netcracker.edu.tms.project.model.ProjectTeam;
//import com.netcracker.edu.tms.user.model.User;
//import com.netcracker.edu.tms.user.service.UserService;
//import org.junit.Assert;
//import org.junit.Test;
//
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.ArgumentMatchers.any;
//
//public class ProjectServiceImplTest {
//
//    private static final String TEST_NAME = "test";
//
//    @Mock
//    private ProjectDao projectDao = mock(ProjectDao.class);
//
//    @Mock
//    private MailService mailService = mock(MailService.class);
//
//    @Mock
//    private UserService userService = mock(UserService.class);
//
//    private ProjectService projectService = new ProjectServiceImpl(projectDao, mailService, userService);
//
//    @Test
//    public void getProjectById() throws Exception {
//        Project expected = new Project(BigInteger.ONE, BigInteger.ONE, TEST_NAME);
//        Mockito.when(projectDao.getProjectById(expected.getId())).thenReturn(expected);
//        Project actual = projectService.getProjectById(expected.getId());
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void addProject() throws Exception {
//        Project inserted = new Project(BigInteger.ONE, BigInteger.TEN, "inserted");
//        Mockito.when(projectDao.addProject(inserted)).thenReturn(true);
//        Assert.assertTrue(projectDao.addProject(inserted));
//    }
//
//    @Test
//    public void updateProject() throws Exception {
//        Mockito.when(projectDao.updateProject(any(Project.class), any(BigInteger.class))).thenReturn(true);
//        Assert.assertTrue(projectDao.updateProject(new Project(BigInteger.ONE, BigInteger.TEN, "test"), BigInteger.TEN));
//    }
//
//    @Test
//    public void deleteProject() throws Exception {
//        Mockito.when(projectDao.deleteProject(any())).thenReturn(true);
//        Assert.assertTrue(projectDao.deleteProject(BigInteger.ONE));
//    }
//
//    @Test
//    public void findProjectsByCreatorId() throws Exception {
//        Project expected = new Project(BigInteger.ONE, BigInteger.ONE, TEST_NAME);
//        Mockito.when(projectDao.getProjectById(expected.getCreatorId())).thenReturn(expected);
//        Project actual = projectService.getProjectById(expected.getCreatorId());
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getAllProjects() throws Exception {
//        List<Project> expected = new ArrayList<>();
//        expected.add(new Project(BigInteger.ONE, BigInteger.ONE, "test1"));
//        expected.add(new Project(BigInteger.valueOf(2), BigInteger.ONE, "test2"));
//        expected.add(new Project(BigInteger.valueOf(2), BigInteger.ONE, "test3"));
//        expected.add(new Project(BigInteger.valueOf(2), BigInteger.ONE, "test4"));
//
//        Mockito.when(projectDao.getAllProjects()).thenReturn(expected);
//        List<ProjectWithCreator> actual = projectService.getAllProjectsWithCreators();
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getProjectByName() throws Exception {
//        Project expected = new Project(BigInteger.ONE, BigInteger.ONE, "name1");
//        Mockito.when(projectDao.getProjectByName("name1")).thenReturn(expected);
//        Project actual = projectService.getProjectByName("name1");
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void setProjectsTeam() throws Exception {
//        List<User> toInsert = new ArrayList<>();
//        toInsert.add(new User("fullName1", "email1"));
//        toInsert.add(new User("fullName2", "email2"));
//        toInsert.add(new User("fullName3", "email3"));
//        toInsert.add(new User("fullName4", "email4"));
//
//        Mockito.when(projectDao.addUsersToProjects(any(ProjectTeam.class))).thenReturn(true);
//
////        Assert.assertTrue(projectService.setProjectsTeam(toInsert, BigInteger.ONE));
//    }
//
//    @Test
//    public void getTasksByUserId() throws Exception {
//        Task taskToReturn = new Task();
//        List<Task> toReturn = new ArrayList<>();
//        toReturn.add(taskToReturn);
//        Mockito.when(projectDao.getTasksByUserId(any(BigInteger.class))).thenReturn(toReturn);
////        Assert.assertEquals(toReturn, projectService.getTasksByUserId(BigInteger.ONE));
//    }
//
//    @Test
//    public void getTeamByProjectId() throws Exception {
//        List<User> team = new ArrayList<>();
//        team.add(new User("fullName1", "email1"));
//        team.add(new User("fullName2", "email2"));
//        team.add(new User("fullName3", "email3"));
//        team.add(new User("fullName4", "email4"));
//
//        Mockito.when(projectDao.getTeamByProjectId(any(BigInteger.class))).thenReturn(team);
//        Assert.assertEquals(team, projectService.getTeamByProject(BigInteger.TEN));
//    }
//
//    @Test
//    public void deleteUserFromTeam() throws Exception {
//        Mockito.when(projectDao.deleteUserFromTeam(any(User.class), any(BigInteger.class))).thenReturn(true);
//        Assert.assertTrue(projectService.deleteUserFromTeam(new User(), BigInteger.TEN));
//    }
//}