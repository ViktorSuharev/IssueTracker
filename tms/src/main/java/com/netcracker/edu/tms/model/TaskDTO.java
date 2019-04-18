package com.netcracker.edu.tms.model;

import com.netcracker.edu.tms.service.ProjectService;
import com.netcracker.edu.tms.service.UserService;
import lombok.Data;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        User assignee = userService.getUserByID(assigneeId);
        User reporter = userService.getUserByID(reporterId);
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
