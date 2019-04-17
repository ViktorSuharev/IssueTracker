package com.netcracker.edu.tms.web;

import com.netcracker.edu.tms.model.Priority;
import com.netcracker.edu.tms.model.Status;
import com.netcracker.edu.tms.model.Task;
import com.netcracker.edu.tms.service.TaskService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

@RequestMapping(value = "/tasks")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity getTaskById(@PathVariable BigInteger taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/getTaskByName")
    public List<Task> getTaskByName(@RequestParam("taskName") String taskName) {
        return taskService.getTaskByName(taskName);
    }

    @PostMapping
    public ResponseEntity addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
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
                    taskService.getTaskById(taskId).getTaskId() +
                    " not found", ex);
        }

    }

    @GetMapping("/reporter")
    public @ResponseBody List<Task> getTaskByReporter(@RequestParam("reporterId") BigInteger reporterId) { //argument should be User instance
        return taskService.getTaskByReporter(reporterId);
    }

    @GetMapping("/assignee")
    public @ResponseBody List<Task> getTaskByAssignee(@RequestParam("assigneeId") BigInteger assigneeId) { //argument should be User instance
        return taskService.getTaskByAssignee(assigneeId);
    }

    @GetMapping("/creationDate")
    public @ResponseBody List<Task> getTaskByCreationDate(@RequestParam("creationDate")
                                                          @DateTimeFormat(pattern = "dd/MM/yyyy") String creationDate) throws ParseException {
        return taskService.getTaskByCreationDate(creationDate);
    }

    @GetMapping("/project")
    public @ResponseBody List<Task> getTaskByProject(@RequestParam("projectId") BigInteger projectId){//argument should be Project instance
        return taskService.getTaskByProject(projectId);
    }

    @GetMapping("/status")
    public @ResponseBody List<Task> getTaskByStatus(@RequestParam("taskStatus") Status taskStatus){
        return taskService.getTaskByStatus(taskStatus);
    }

    @GetMapping("/priority")
    public @ResponseBody List<Task> getTaskByPriority(@RequestParam("taskPriority") Priority taskPriority){
        return taskService.getTaskByPriority(taskPriority);
    }

}
