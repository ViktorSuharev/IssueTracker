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

@RequestMapping(value = "/api/tasks")
@RestController
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public Iterable<Task> getAllTasks() {
        return taskService.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/active")
    public Iterable<Task> getAllActiveTasks() {
        return taskService.getAllActiveTasks();
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity addTask(@RequestBody TaskDTO task) {
        Task t = task.convert(userService, projectService);

        //sending notifiction to assignee
        taskService.sendNewTaskToAssignee(t);

        if(taskService.addTask(t))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ReporterOrAssigneeTasks getMyTasks(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());

        Iterable<Task> asAssignee = taskService.getActiveTasksByAssignee(user);
        Iterable<Task> asReporter = taskService.getResolvedTasksByReporter(user);

        ReporterOrAssigneeTasks tasks = new ReporterOrAssigneeTasks(asAssignee, asReporter);
        return tasks;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/project/{id}")
    public @ResponseBody Iterable<Task> getTaskByProject(@PathVariable("id") BigInteger projectId){
        Project project = projectService.getProjectById(projectId);
        return taskService.getTaskByProject(project);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable BigInteger id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/history/{id}")
    public ResponseEntity<Iterable<History>> getTaskHistoryByTaskId(@PathVariable BigInteger id) {
        return ResponseEntity.ok(taskService.getHistoryByTaskId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity updateTask(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @PathVariable BigInteger id,
                                     @RequestBody TaskWithComment taskWithComment) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());
        Task task = taskWithComment.getTask().convert(userService, projectService);
        task.setId(id);
        String comment = taskWithComment.getComment();
        taskService.taskUpdateMailNotification(task, comment, user);
        return ResponseEntity.ok(taskService.updateTask(task, comment, user));
    }

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/reporter")
    public @ResponseBody Iterable<Task> getTaskByReporter(@RequestParam("reporterId") BigInteger reporterId) {
        User reporter = userService.getUserById(reporterId);
        return taskService.getTaskByReporter(reporter);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/assignee")
    public @ResponseBody Iterable<Task> getTaskByAssignee(@RequestParam("assigneeId") BigInteger assigneeId) {
        User assignee = userService.getUserById(assigneeId);
        return taskService.getTaskByAssignee(assignee);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/creationDate")
    public @ResponseBody List<Task> getTaskByCreationDate(@RequestParam("creationDate")
                                                          @DateTimeFormat(pattern = "dd/MM/yyyy") String creationDate) throws ParseException {
        return taskService.getTaskByCreationDate(creationDate);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/status")
    public @ResponseBody List<Task> getTaskByStatus(@RequestParam("status") Status status){
        return taskService.getTaskByStatus(status);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/priority")
    public @ResponseBody List<Task> getTaskByPriority(@RequestParam("taskPriority") Priority taskPriority){
        return taskService.getTaskByPriority(taskPriority);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/active/assignee/{id}")
    public @ResponseBody Iterable<Task> getActiveTasksByAssignee(@PathVariable(name = "id") BigInteger id){
        User user = userService.getUserById(id);
        return taskService.getActiveTasksByAssignee(user);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/active/project/{id}")
    public @ResponseBody Iterable<Task> getActiveTasksByProject(@PathVariable(name = "id") BigInteger id){
        Project project = projectService.getProjectById(id);
        return taskService.getActiveTasksByProject(project);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resolved/project/{id}")
    public @ResponseBody Iterable<Task> getResolvedTasksByProject(@PathVariable(name = "id") BigInteger id){
        Project project = projectService.getProjectById(id);
        return taskService.getResolvedTasksByProject(project);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resolved/assignee/{id}")
    public @ResponseBody Iterable<Task> getResolvedTasksByAssignee(@PathVariable(name = "id") BigInteger id){
        User assignee = userService.getUserById(id);
        return taskService.getResolvedTasksByAssignee(assignee);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/closed/assignee/{id}")
    public @ResponseBody Iterable<Task> getClosedTasksByAssignee(@PathVariable(name = "id") BigInteger id){
        User assignee = userService.getUserById(id);
        return taskService.getClosedTasksByAssignee(assignee);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resolved/reporter/{id}")
    public @ResponseBody Iterable<Task> getResolvedTasksByReporter(@PathVariable(name = "id") BigInteger id){
        User reporter = userService.getUserById(id);
        return taskService.getResolvedTasksByReporter(reporter);
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
