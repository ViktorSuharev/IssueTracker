package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.model.WrapperObject;
import com.netcracker.edu.tms.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectRESTController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Project> addNewProject(@RequestBody WrapperObject wrapperObject) {
       Project newProject=wrapperObject.getNewProject();
       List<User> addedUsers=wrapperObject.getAddedUsers();

       System.out.println(newProject.toString()+addedUsers.toString());

        Project newProj = new Project(null, newProject.getCreator_id(), newProject.getName());
        boolean ret = projectService.addProject(newProj);

        if(addedUsers==null||addedUsers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean retAddedUsers=projectService.setProjectsTeam(addedUsers,newProj.getId());

        if (!ret) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.getUsersProjects(userId), HttpStatus.OK);
    }

    @GetMapping("/createdby/{id}")
    public ResponseEntity<List<Project>> getProjectsByCreatorId(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.findProjectsByCreatorId(userId), HttpStatus.OK);
    }

}
