//package com.netcracker.edu.tms.dao;
//
//import com.netcracker.edu.tms.model.Project;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
///**
// * Project
// * id creatorId name
// */
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@Import(ProjectDaoJpaImpl.class)
//
//public class ProjectDaoJpaImplTest {
//
//    @Autowired
//    private ProjectDao projectDao;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testGetProjectByIdExist() {
//        Project expected = new Project(null, BigInteger.valueOf(1L), "name1");
//        BigInteger id = (BigInteger) entityManager.persistAndGetId(expected);
//        expected.setId(id);
//
//        Project actual = projectDao.getProjectById(id);
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetProjectByIdNotExist() {
//        Project actual = projectDao.getProjectById(BigInteger.valueOf(10L));
//        Assert.assertNull(actual);
//    }
//
//    @Test
//    public void testGetProjectByCorrectNameWithProject() {
//        Project expected = new Project(null, BigInteger.ONE, "name1");
//        entityManager.persist(expected);
//        Project actual = projectDao.getProjectByName("name1");
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetProjectByCorrectNameWOProject() {
//        Project expected = null;
//        Project actual = projectDao.getProjectByName("name99");
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    @Transactional
//    public void testUpdateProjectWithExistentId() {
//        Project oldProject = new Project(null, BigInteger.TEN, "name111");
//        BigInteger id = (BigInteger) entityManager.persistAndGetId(oldProject);
//        Project newProject = new Project(id, BigInteger.TEN, "name1");
//        Assert.assertTrue(projectDao.updateProject(newProject, id));
//    }
//
//    @Test
//    @Transactional
//    public void testUpdateProjectWithNonExistentId() {
//        Project newProject = new Project(BigInteger.TEN, BigInteger.TEN, "name111");
//        Assert.assertFalse(projectDao.updateProject(newProject, newProject.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void testDeleteProjectWithExistentId() {
//        Project oldProject = new Project(null, BigInteger.TEN, "name111");
//        BigInteger id = (BigInteger) entityManager.persistAndGetId(oldProject);
//        Assert.assertTrue(projectDao.deleteProject(id));
//    }
//
//    @Test
//    @Transactional
//    public void testDeleteProjectWithNonExistentId() {
//        Assert.assertFalse(projectDao.deleteProject(BigInteger.TEN));
//    }
//
//    @Test
//    public void testFindProjectsByCreatorIdWithProjects() {
//        List<Project> expected = new ArrayList<>();
//        expected.add(new Project(null, BigInteger.TEN, "name1"));
//        expected.add(new Project(null, BigInteger.TEN, "name2"));
//        for (Project in : expected) {
//            entityManager.persist(in);
//        }
//        List<Project> actualReturn = projectDao.findProjectsByCreatorId(BigInteger.TEN);
//        Assert.assertEquals(expected, actualReturn);
//    }
//
//    @Test
//    public void testFindProjectsByCreatorIdWOProjects() {
//        List<Project> expected = null;
//        List<Project> actual = projectDao.findProjectsByCreatorId(BigInteger.TEN);
//        Assert.assertNotEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetAllProjects() {
//        List<Project> expected = new ArrayList<>();
//        expected.add(new Project(null, BigInteger.ONE, "name1"));
//        expected.add(new Project(null, BigInteger.valueOf(2), "name2"));
//        expected.add(new Project(null, BigInteger.valueOf(3), "name3"));
//        expected.add(new Project(null, BigInteger.valueOf(4), "name4"));
//        expected.add(new Project(null, BigInteger.valueOf(5), "name5"));
//        expected.add(new Project(null, BigInteger.valueOf(1), "name6"));
//        for (Project in : expected) {
//            entityManager.persist(in);
//        }
//
//        List<Project> actual = projectDao.getAllProjects();
//
//        Assert.assertEquals(expected, actual);
//
//    }
//
//}