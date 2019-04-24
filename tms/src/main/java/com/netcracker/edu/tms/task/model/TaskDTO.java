package com.netcracker.edu.tms.task.model;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import lombok.Data;

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

    private Status status;

    private String dueDate;

    private String description;

    public Task convert(UserService userService, ProjectService projectService) {
        User assignee = userService.getUserById(assigneeId);
        User reporter = userService.getUserById(reporterId);
        Project project = projectService.getProjectById(projectId);

        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S").parse(this.dueDate);
        } catch (ParseException e) {
            try {
                dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.dueDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }


        return new Task(name, description, dueDate, reporter, assignee, project, priority, status);
    }
}
