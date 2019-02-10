package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.dao.ProjectDao;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

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
    public Project getProjectsByName(String name) {
        return projectDao.getProjectByName(name);
    }

    @Override
    public List<Project> getUsersProjects(BigInteger userId) {
        return projectDao.getUsersProjects(userId);
    }

    @Override
    @Transactional
    public boolean setProjectsTeam(List<User> addedUsers, BigInteger id) {
        return projectDao.setProjectsTeam(addedUsers, id);
    }
}
