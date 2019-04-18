package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.*;
import com.netcracker.edu.tms.security.UserPrincipal;
import com.netcracker.edu.tms.service.ProjectService;
import com.netcracker.edu.tms.service.TaskService;
import com.netcracker.edu.tms.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class TaskController {

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

    @PostMapping("/")
    public ResponseEntity addTask(@RequestBody TaskDTO task) {
        Task t = task.convert(userService, projectService);
        if(taskService.addTask(t))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ReporterOrAssigneeTasks getMyTasks(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());

        Iterable<Task> asAssignee = taskService.getTaskByAssignee(user);
        Iterable<Task> asReporter = taskService.getTaskByReporter(user);

        ReporterOrAssigneeTasks tasks = new ReporterOrAssigneeTasks(asAssignee, asReporter);
        return tasks;
    }

    @GetMapping("/project/{id}")
    public @ResponseBody Iterable<Task> getTaskByProject(@PathVariable("id") BigInteger projectId){//argument should be Project instance
        Project project = projectService.getProjectById(projectId);
        return taskService.getTaskByProject(project);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{taskId}")
    public ResponseEntity getTaskById(@PathVariable BigInteger taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getTaskByName")
    public List<Task> getTaskByName(@RequestParam("taskName") String taskName) {
        return taskService.getTaskByName(taskName);
    }

    @PutMapping
    public ResponseEntity updateTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @DeleteMapping("/{taskId}")
    ResponseEntity deleteTask(@PathVariable BigInteger taskId) {
        try {
            return ResponseEntity.ok(taskService.deleteTask(taskService.getTaskById(taskId)));
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task " +
                    taskService.getTaskById(taskId).getId() +
                    " not found", ex);
        }
    }

    @GetMapping("/reporter")
    public @ResponseBody Iterable<Task> getTaskByReporter(@RequestParam("reporterId") BigInteger reporterId) { //argument should be User instance
        User reporter = userService.getUserByID(reporterId);
        return taskService.getTaskByReporter(reporter);
    }

    @GetMapping("/assignee")
    public @ResponseBody Iterable<Task> getTaskByAssignee(@RequestParam("assigneeId") BigInteger assigneeId) { //argument should be User instance
        User assignee = userService.getUserByID(assigneeId);
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
}
