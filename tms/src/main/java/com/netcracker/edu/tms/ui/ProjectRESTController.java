package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Project> addNewProject(@RequestBody Project newProject){
        Project newProj=new Project(null,newProject.getCreator_id(),newProject.getName());
        boolean ret=projectService.addProject(newProj);
        if(!ret){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newProject,HttpStatus.OK);
    }

}
