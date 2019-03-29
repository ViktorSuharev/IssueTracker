package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;

import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.model.ProjectInfo;
import com.netcracker.edu.tms.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectRestController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Project> addNewProject(@RequestBody ProjectInfo projectInfo) {
        Project newProject = projectInfo.getNewProject();
        List<User> addedUsers = projectInfo.getAddedUsers();

        if (addedUsers == null || addedUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //this block must be before last return line, because in case of invalid addProject function block must not be executed
        //start of the  block
        projectService.sendInvitationToNewProject(addedUsers, newProject);
        //end of the block

        Project newProj = new Project(null, newProject.getCreatorId(), newProject.getName());
        boolean retAddedUsers = projectService.setProjectsTeam(addedUsers, newProj.getId());

        boolean operationResult = projectService.addProject(newProj);
        if (!operationResult) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectInfo projectInfo, @PathVariable BigInteger projectId) {
        Project projectToUpdate = projectInfo.getNewProject();
        List<User> addedUsers = projectInfo.getAddedUsers();
        if (addedUsers == null || addedUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean retAddedUsers = projectService.setProjectsTeam(addedUsers, projectToUpdate.getId());
        boolean operationResult = projectService.updateProject(projectToUpdate, projectId);
        if (!operationResult) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(projectToUpdate, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.getProjectsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<User>> getTeamfromProjectId(@PathVariable(name = "id", required = true) BigInteger projectId) {
        return new ResponseEntity<>(projectService.getTeamByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getNamefromProjectId(@PathVariable(name = "id", required = true) BigInteger projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId).getName(), HttpStatus.OK);
    }
}
