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
        try{
            return projectDao.getProjectById(projectId);
        }catch (IllegalArgumentException e){
            Project empl=new Project(BigInteger.valueOf(-1),BigInteger.valueOf(1),BigInteger.valueOf(1),"stub");
            return empl;
        }
    }

    @Override
    @Transactional
    public boolean addProject(Project newProject) {
        try{
            return projectDao.addProject(newProject);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateProject(Project project, Project oldProject) {
        try{
            return projectDao.updateProject(project,oldProject);
        }catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteProject(Project project) {
        try{
            return projectDao.deleteProject(project);
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }
}
