package com.netcracker.edu.tms.task.controller;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.task.model.*;
import com.netcracker.edu.tms.task.service.TaskService;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

@PreAuthorize("hasRole('USER')")
@RequestMapping(value = "/api/tasks")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public Iterable<Task> getAllTasks() {
        return taskService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity addTask(@RequestBody TaskDTO task) {
        Task t = task.convert(userService, projectService);
        if(taskService.addTask(t))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/my")
    public ReporterOrAssigneeTasks getMyTasks(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());

        Iterable<Task> asAssignee = taskService.getTaskByAssignee(user);
        Iterable<Task> asReporter = taskService.getTaskByReporter(user);

        ReporterOrAssigneeTasks tasks = new ReporterOrAssigneeTasks(asAssignee, asReporter);
        return tasks;
    }

    @GetMapping("/project/{id}")
    public @ResponseBody Iterable<Task> getTaskByProject(@PathVariable("id") BigInteger projectId){
        Project project = projectService.getProjectById(projectId);
        return taskService.getTaskByProject(project);
    }


    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable BigInteger id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<Iterable<History>> getTaskHistoryByTaskId(@PathVariable BigInteger id) {
        return ResponseEntity.ok(taskService.getHistoryByTaskId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @PathVariable BigInteger id,
                                     @RequestBody TaskWithComment taskWithComment) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());
        Task task = taskWithComment.getTask().convert(userService, projectService);
        task.setId(id);
        String comment = taskWithComment.getComment();
        return ResponseEntity.ok(taskService.updateTask(task, comment, user));
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteTask(@PathVariable BigInteger id) {
        try {
            return ResponseEntity.ok(taskService.deleteTask(taskService.getTaskById(id)));
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task " +
                    taskService.getTaskById(id).getId() +
                    " not found", ex);
        }
    }

    @GetMapping("/reporter")
    public @ResponseBody Iterable<Task> getTaskByReporter(@RequestParam("reporterId") BigInteger reporterId) {
        User reporter = userService.getUserById(reporterId);
        return taskService.getTaskByReporter(reporter);
    }

    @GetMapping("/assignee")
    public @ResponseBody Iterable<Task> getTaskByAssignee(@RequestParam("assigneeId") BigInteger assigneeId) {
        User assignee = userService.getUserById(assigneeId);
        return taskService.getTaskByAssignee(assignee);
    }

    @GetMapping("/creationDate")
    public @ResponseBody List<Task> getTaskByCreationDate(@RequestParam("creationDate")
                                                          @DateTimeFormat(pattern = "dd/MM/yyyy") String creationDate) throws ParseException {
        return taskService.getTaskByCreationDate(creationDate);
    }

    @GetMapping("/status")
    public @ResponseBody List<Task> getTaskByStatus(@RequestParam("taskStatus") Status taskStatus){
        return taskService.getTaskByStatus(taskStatus);
    }

    @GetMapping("/priority")
    public @ResponseBody List<Task> getTaskByPriority(@RequestParam("taskPriority") Priority taskPriority){
        return taskService.getTaskByPriority(taskPriority);
    }

    @AllArgsConstructor
    @Data
    private class ReporterOrAssigneeTasks{
        private Iterable<Task> toDo;
        private Iterable<Task> toReport;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class TaskWithComment{
        private TaskDTO task;
        private String comment;
    }
}
