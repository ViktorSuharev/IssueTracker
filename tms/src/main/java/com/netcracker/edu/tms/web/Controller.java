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
public class Controller {
    @Autowired
    private TaskService taskService;

    @GetMapping("/getTaskById")
    public ResponseEntity<Task> getTaskById(@RequestParam(value = "taskId", required = true) BigInteger taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/listOfTasksByName")
    public @ResponseBody
    List<Task> listOfTasksByName(@RequestParam("taskName") String taskName) {
        return taskService.listOfTasksByName(taskName);
    }

    @PostMapping()
    boolean addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping
    boolean updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping
    ResponseEntity deleteTask(@RequestBody Task task) {
        try {
            return new ResponseEntity(taskService.deleteTask(task), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task " + task.getTaskId() + " not found", ex);
        }

    }

    @GetMapping("/listOfTasksByReporter")
    public @ResponseBody
    List<Task> listOfTasksByReporter(@RequestParam("reporterId") BigInteger reporterId) { //argument should be User instance
        return taskService.listOfTasksByReporter(reporterId);
    }

    @GetMapping("/listOfTasksByAssignee")
    public @ResponseBody
    List<Task> listOfTasksByAssignee(@RequestParam("assigneeId") BigInteger assigneeId) { //argument should be User instance
        return taskService.listOfTasksByAssignee(assigneeId);
    }

    @GetMapping("/listOfTasksByCreationDate")
    public @ResponseBody
    List<Task> listOfTasksByCreationDate(@RequestParam("creationDate")
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") String creationDate) throws ParseException {
        return taskService.listOfTasksByCreationDate(creationDate);
    }

    @GetMapping("/listOfTasksByProject")
    List<Task> listOfTasksByProject(@RequestParam("projectId") BigInteger projectId){//argument should be Project instance
        return taskService.listOfTasksByProject(projectId);
    }

    @GetMapping("/listOfTasksByStatus")
    List<Task> listOfTasksByStatus(@RequestParam("taskStatus") Status taskStatus){
        return taskService.listOfTasksByStatus(taskStatus);
    }

    @GetMapping("/listOfTasksByPriority")
    List<Task> listOfTasksByPriority(@RequestParam("taskPriority") Priority taskPriority){
        return taskService.listOfTasksByPriority(taskPriority);
    }

}
