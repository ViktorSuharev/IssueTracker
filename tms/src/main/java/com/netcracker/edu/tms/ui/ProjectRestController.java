package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.ProjectInfo;
import com.netcracker.edu.tms.model.ProjectMember;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<Project> addNewProject(@RequestBody ProjectInfo projectInfo) {
        Project project = projectInfo.getProject();
        List<ProjectMember> team = projectInfo.getTeam();

        if (project == null || team == null || team.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //save project
        if(! projectService.addProject(project))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //getId
        project = projectService.getProjectByName(project.getName());

        //add team
        projectService.setProjectsTeam(project.getId(), team);

        //notify team
        projectService.sendInvitationToNewProject(project, team);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectInfo projectInfo, @PathVariable BigInteger projectId) {
        Project projectToUpdate = projectInfo.getProject();
        List<ProjectMember> team = projectInfo.getTeam();

        if (team == null || team.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean retAddedUsers = projectService.setProjectsTeam(projectToUpdate.getId(), team);
        boolean operationResult = projectService.updateProject(projectToUpdate, projectId);

        if (!operationResult || !retAddedUsers)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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
