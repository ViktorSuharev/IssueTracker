package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.netcracker.edu.tms.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;


@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public String getAllProjects(Model model) {

        List<Project> allProjects = projectService.getAllProjects();

        model.addAttribute("projects", allProjects);

        return "all";
    }

    @GetMapping("/find")
    public String findById(Model model, @RequestParam(value = "id", required = false) String id) {
        if (!ControllersUtils.isFilled(id)) {
            model.addAttribute("project", null);
            return "find";
        }

        BigInteger projectId = ControllersUtils.toBigInteger(id);


        Project project = projectId != null ? projectService.getProjectById(projectId) : null;
        project = project != null ? project : ControllersUtils.getNonexistentProject();

        model.addAttribute("project", project);

        return "find";
    }


    @GetMapping("/delete")
    public String deleteProject(Model model, @RequestParam(value = "id", required = false) String id) {
        if (!ControllersUtils.isFilled(id)) {
            model.addAttribute("project", null);
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "delete";
        }
        BigInteger projectId = ControllersUtils.toBigInteger(id);

        if (projectId != null) {
            Project projectToDelete = projectService.getProjectById(projectId);
            projectToDelete = projectToDelete != null ? projectToDelete : ControllersUtils.getNonexistentProject();
            projectService.deleteProject(projectToDelete);

            model.addAttribute("projectToDelete", projectToDelete);


        } else {
            Project projectToDelete = ControllersUtils.getNonexistentProject();
            model.addAttribute("projectToDelete", projectToDelete);
        }

        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projects", projectList);

        return "delete";
    }

    @GetMapping("/update")
    public String updateProject(@RequestParam(value = "id", required = false) String id,
                                @RequestParam(value = "creator_id", required = false) String creator_id,
                                @RequestParam(value = "name", required = false) String name,
                                Model model) {

        if (!ControllersUtils.isFilled(id) || !ControllersUtils.isFilled(creator_id) || !ControllersUtils.isFilled(name)) {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "update";
        }


        Project newProject = new Project(ControllersUtils.toBigInteger(id), ControllersUtils.toBigInteger(creator_id), name);
        if (newProject.getId().equals(BigInteger.valueOf(-1)) || newProject.getCreator_id().equals(BigInteger.valueOf(-1))) {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "update";
        }

        Project oldProject = projectService.getProjectById(newProject.getId());
        oldProject = oldProject != null ? oldProject : ControllersUtils.getNonexistentProject();


        if (projectService.updateProject(newProject, oldProject)) {
            model.addAttribute("newProject", newProject);
        } else {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
        }


        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projects", projectList);
        return "update";
    }


    @GetMapping("/create")
    public String createProject(@RequestParam(value = "creator_id", required = false) String creator_id,
                                @RequestParam(value = "name", required = false) String name,
                                Model model) {
        if (creator_id == null || name == null) {

            model.addAttribute("addedProject", null);
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "create";
        } else {
            Project newProject = new Project(null, ControllersUtils.toBigInteger(creator_id), name);

            projectService.addProject(newProject);
            model.addAttribute("addedProject", newProject);

            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
        }


        return "create";
    }

    @GetMapping("/findProjectsByCreatorId")
    public String findProjetsByCreatorId(@RequestParam(value = "creator_id",required = false) String creator_id, Model model){

        if (!ControllersUtils.isFilled(creator_id)) {
            model.addAttribute("creator_id", -1);

            return "findProjectsByCreatorId";
        }



        BigInteger creatorId = ControllersUtils.toBigInteger(creator_id);
        List<Project> ret= creatorId!=null? projectService.findProjectsByCreatorId(creatorId)
                                                                   : Collections.emptyList();
        model.addAttribute("projects",ret);
        model.addAttribute("creator_id",creatorId);

        return "findProjectsByCreatorId";
    }
}
