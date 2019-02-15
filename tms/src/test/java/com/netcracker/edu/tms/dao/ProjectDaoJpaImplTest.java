package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Project;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * id creatorId name
 * 1      1      name1
 * 2      2      name2
 * 3      3      name2
 * 4      4      name3
 * 5      5      name4
 * 6      1      name6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProjectDaoJpaImplTest {

    @Autowired
    private ProjectDao projectDao;

    private static EmbeddedDatabase database;

    @BeforeClass
    public static void setup() throws SQLException {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2).setName("testdb")
                .addScript("createTestDb.sql").build();
    }

    @Before
    public void setupData() throws Exception {
        database.getConnection().createStatement()
                .execute(getContentByResourceRelativePath("deleteData.sql"));
        database.getConnection().createStatement()
                .execute(getContentByResourceRelativePath("fillData.sql"));
    }

    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }

    @Test
    public void testGetProjectByIdExist() {
        Project actual;
        actual = projectDao.getProjectById(BigInteger.valueOf(1L));
        Project expected = new Project(BigInteger.valueOf(1L), BigInteger.valueOf(1L), "name1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetProjectByIdNotExist() {
        Project actual = projectDao.getProjectById(BigInteger.valueOf(10L));
        Assert.assertNull(actual);
    }

    @Test
    public void testGetProjectByCorrectNameWithProject() {
        Project expected = new Project(BigInteger.ONE, BigInteger.ONE, "name1");
        Project actual = projectDao.getProjectByName("name1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetProjectByCorrectNameWOProject() {
        Project expected = null;
        Project actual = projectDao.getProjectByName("name99");
        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void testUpdateProjectWithExistentId() {
        Project newProject = new Project(BigInteger.ONE, BigInteger.TEN, "name111");
        Assert.assertTrue(projectDao.updateProject(newProject, newProject.getId()));
    }

    @Test
    @Transactional
    public void testUpdateProjectWithNonExistentId() {
        Project newProject = new Project(BigInteger.TEN, BigInteger.TEN, "name111");
        Assert.assertFalse(projectDao.updateProject(newProject, newProject.getId()));
    }

    @Test
    @Transactional
    public void testDeleteProjectWithExistentId() {
        Assert.assertTrue(projectDao.deleteProject(BigInteger.ONE));
    }

    @Test
    @Transactional
    public void testDeleteProjectWithNonExistentId() {
        Assert.assertFalse(projectDao.deleteProject(BigInteger.TEN));
    }

    @Test
    public void testFindProjectsByCreatorIdWithProjects() {
        List<Project> expected = new ArrayList<>();
        expected.add(projectDao.getProjectById(BigInteger.ONE));
        expected.add(projectDao.getProjectById(BigInteger.valueOf(6)));
        List<Project> actual = projectDao.findProjectsByCreatorId(BigInteger.ONE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindProjectsByCreatorIdWOProjects() {
        List<Project> expected = null;
        List<Project> actual = projectDao.findProjectsByCreatorId(BigInteger.TEN);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void testGetAllProjects() {
        List<Project> expected = new ArrayList<>();
        expected.add(new Project(BigInteger.ONE, BigInteger.ONE, "name1"));
        expected.add(new Project(BigInteger.valueOf(2), BigInteger.valueOf(2), "name2"));
        expected.add(new Project(BigInteger.valueOf(3), BigInteger.valueOf(3), "name3"));
        expected.add(new Project(BigInteger.valueOf(4), BigInteger.valueOf(4), "name4"));
        expected.add(new Project(BigInteger.valueOf(5), BigInteger.valueOf(5), "name5"));
        expected.add(new Project(BigInteger.valueOf(6), BigInteger.valueOf(1), "name6"));

        List<Project> actual = projectDao.getAllProjects();

        Assert.assertEquals(expected, actual);

    }

    private String getContentByResourceRelativePath(String path)
            throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        return new String(
                Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}