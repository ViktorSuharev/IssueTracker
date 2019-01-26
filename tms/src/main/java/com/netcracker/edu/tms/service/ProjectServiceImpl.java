package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.dao.ProjectDao;
import com.netcracker.edu.tms.model.Project;
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
        try {
            return projectDao.addProject(newProject);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateProject(Project project, Project oldProject) {

        return projectDao.updateProject(project, oldProject);

    }

    @Override
    @Transactional
    public boolean deleteProject(Project project) {
        try {
            return projectDao.deleteProject(project);
        } catch (IllegalArgumentException e) {
            return false;

        } catch (NullPointerException e) {
            return false;
        }
    }
    @Override
    public List<Project> findProjectsByCreatorId(BigInteger creator_id){
        return projectDao.findProjectsByCreatorId(creator_id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }
}
