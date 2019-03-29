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

        if (!projectId.equals(BigInteger.valueOf(-1))) {
            Project projectToDelete = projectService.getProjectById(projectId);
            projectToDelete = projectToDelete != null ? projectToDelete : ControllersUtils.getNonexistentProject();
            projectService.deleteProject(projectId);

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
                                @RequestParam(value = "creatorId", required = false) String creatorId,
                                @RequestParam(value = "name", required = false) String name,
                                Model model) {

        if (!ControllersUtils.isFilled(id) || !ControllersUtils.isFilled(creatorId) || !ControllersUtils.isFilled(name)) {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "update";
        }

        Project newProject = new Project(ControllersUtils.toBigInteger(id), ControllersUtils.toBigInteger(creatorId), name);
        if (newProject.getId().equals(BigInteger.valueOf(-1)) || newProject.getCreatorId().equals(BigInteger.valueOf(-1))) {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "update";
        }

        Project oldProject = projectService.getProjectById(newProject.getId());
        oldProject = oldProject != null ? oldProject : ControllersUtils.getNonexistentProject();

        if (projectService.updateProject(newProject, ControllersUtils.toBigInteger(id))) {
            model.addAttribute("newProject", newProject);
        } else {
            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
        }

        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projects", projectList);
        return "update";
    }

    @GetMapping("/create")
    public String createProject(@RequestParam(value = "creatorId", required = false) String creatorId,
                                @RequestParam(value = "name", required = false) String name,
                                Model model) {
        if (creatorId == null || name == null) {

            model.addAttribute("addedProject", null);
            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "create";
        } else {
            Project newProject = new Project(null, ControllersUtils.toBigInteger(creatorId), name);
            if (newProject.getCreatorId().compareTo(BigInteger.ONE) < 0) {
                model.addAttribute("addedProject", null);
                List<Project> projectList = projectService.getAllProjects();
                model.addAttribute("projects", projectList);
                return "create";
            }
            projectService.addProject(newProject);
            model.addAttribute("addedProject", newProject);

            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
        }

        return "create";
    }

    @GetMapping("/findProjectsByCreatorId")
    public String findProjetsByCreatorId(@RequestParam(value = "creatorId", required = false) String creatorId, Model model) {

        if (!ControllersUtils.isFilled(creatorId)) {
            model.addAttribute("creator_id", -1);

            return "findProjectsByCreatorId";
        }

        BigInteger creatorIdBig = ControllersUtils.toBigInteger(creatorId);
        List<Project> ret = creatorIdBig.compareTo(BigInteger.ONE) > 0 ? projectService.findProjectsByCreatorId(creatorIdBig)
                : Collections.emptyList();
        model.addAttribute("projects", ret);
        model.addAttribute("creator_id", creatorIdBig);

        return "findProjectsByCreatorId";
    }

    @GetMapping("/findProjectByName")
    public String findProjectByName(@RequestParam(value = "name", required = false) String name, Model model) {

        if (!ControllersUtils.isFilled(name)) {
            model.addAttribute("name", -1);
            model.addAttribute("error", -1);

            return "findProjectByName";
        }

        Project ret = projectService.getProjectByName(name);
        if (ret == null) {
            model.addAttribute("project", null);
            model.addAttribute("name", name);
            model.addAttribute("error", 2);
            return "findProjectByName";
        }

        model.addAttribute("project", ret);
        model.addAttribute("name", name);
        model.addAttribute("error", 1);

        return "findProjectByName";
    }
}
