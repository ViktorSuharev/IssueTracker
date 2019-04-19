package com.netcracker.edu.tms.project.controller;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectInfo;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.project.model.ProjectWithCreator;
import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public ProjectRestController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<List<ProjectWithCreator>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjectsWithCreators(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<Project> addNewProject(@AuthenticationPrincipal UserPrincipal currentUser,
                                                 @RequestBody ProjectInfo projectInfo) {
        Project project = projectInfo.getProject();
        project.setCreatorId(userService.getUserByEmail(currentUser.getUsername()).getId());
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
    public ResponseEntity<Project> getAllProjects(@PathVariable(name = "id") BigInteger userId) {
        return new ResponseEntity<>(projectService.getProjectById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<UserWithPassword>> getTeamfromProjectId(@PathVariable(name = "id", required = true) BigInteger projectId) {
        return new ResponseEntity<>(projectService.getTeamByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getNamefromProjectId(@PathVariable(name = "id", required = true) BigInteger projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId).getName(), HttpStatus.OK);
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Iterable<User>> getProjectTeam(@PathVariable(name = "id") BigInteger projectId){
        Project project = projectService.getProjectById(projectId);
        List<UserWithPassword> userWithPasswords = projectService.getTeamByProjectId(project.getId());
        List<User> response = userWithPasswords.stream().map((user) -> User.of(user)).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable(name="id") BigInteger id) {
        if(projectService.deleteProject(id))
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }
}
