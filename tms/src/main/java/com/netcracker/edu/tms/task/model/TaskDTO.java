package com.netcracker.edu.tms.task.model;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.user.service.UserService;
import lombok.Data;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TaskDTO {

    private String name;

    private BigInteger projectId;

    private BigInteger assigneeId;

    private BigInteger reporterId;

    private Priority priority;

    private String dueDate;

    private String description;

    public Task convert(UserService userService, ProjectService projectService) {
        UserWithPassword assignee = userService.getUserByID(assigneeId);
        UserWithPassword reporter = userService.getUserByID(reporterId);
        Project project = projectService.getProjectById(projectId);
        //TODO
//        Date dueDate = null;
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S").parse(this.dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return new Task(name, description, dueDate, reporter, assignee, project, priority);
    }
}
