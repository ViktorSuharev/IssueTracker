package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.netcracker.edu.tms.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigInteger;
import java.util.List;


@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public String getAllProjects(Model model) {

        List<Project> allProjects=projectService.getAllProjects();

        model.addAttribute("projects",allProjects);

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
            return "delete";
        }
        BigInteger projectId = ControllersUtils.toBigInteger(id);

        if (projectId != null) {
            Project projectToDelete=projectService.getProjectById(projectId);
            projectToDelete = projectToDelete != null ? projectToDelete : ControllersUtils.getNonexistentProject();
            projectService.deleteProject(projectToDelete);

            model.addAttribute("projectToDelete", projectToDelete);


        }else{
            Project projectToDelete=ControllersUtils.getNonexistentProject();
            model.addAttribute("projectToDelete", projectToDelete);
        }

        List<Project> projectList = projectService.getAllProjects();
        model.addAttribute("projects", projectList);

        return "delete";
    }

    @GetMapping("/update")
    public String updateProject(@RequestParam(value = "id", required = false,defaultValue = "-1") String id,
                                @RequestParam(value = "creator_id", required = false,defaultValue = "-1") String creator_id,
                                @RequestParam(value = "name", required = false,defaultValue = "-1") String name,
                                Model model) {
        BigInteger oldProjecId = ControllersUtils.toBigInteger(id);
        if(oldProjecId==null){
            Project oldProject = ControllersUtils.getNonexistentProject();
            model.addAttribute("newProject", oldProject);
            return "update";
        }



        if (id==null||creator_id==null||name==null) {

            model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
            return "update";
        } else {
            Project newProject = new Project(new BigInteger(id), new BigInteger(creator_id),name);
            if(newProject.getId()==null){
                model.addAttribute("newProject", ControllersUtils.getNonexistentProject());
                return "update";
            }
            Project oldProject=projectService.getProjectById(newProject.getId());
            projectService.updateProject(newProject,oldProject);
            model.addAttribute("newProject", newProject);


            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
            return "update";
        }

    }

    @GetMapping("/create")
    public String createProject(@RequestParam(value = "creator_id", required = false) String creator_id,
                             @RequestParam(value = "name", required = false) String name,
                             Model model) {
        if (creator_id==null||name==null) {

            model.addAttribute("addedProject", null);
            return "create";
        }else{
            Project newProject=new Project(null,ControllersUtils.toBigInteger(creator_id),name);

            projectService.addProject(newProject);
            model.addAttribute("addedProject", newProject);

            List<Project> projectList = projectService.getAllProjects();
            model.addAttribute("projects", projectList);
        }


        return "create";
    }

}
