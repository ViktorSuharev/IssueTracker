package com.netcracker.edu.tms.user.controller;

import com.netcracker.edu.tms.project.model.DeletedUserFromTeam;
import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    UserService userService;
    private ProjectService projectService;

    @Autowired
    public UserRestController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        List users = StreamSupport.stream(userService.getAllUsers().spliterator(), false).collect(Collectors.toList());
        return new ResponseEntity<>(convertUsersToUsersDTO(users), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<User> aboutMe(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserWithPassword userWithPassword = userService.getUserByEmail(currentUser.getUsername());
        return ResponseEntity.ok(User.of(userWithPassword));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> aboutUser(@PathVariable(name="id") BigInteger id) {
        UserWithPassword userWithPassword = userService.getUserByID(id);
        return ResponseEntity.ok(User.of(userWithPassword));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<Task>> getUsersTasks(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.getTasksByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<Project>> getProjectsByCreatorId(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.findProjectsByCreatorId(userId), HttpStatus.OK);
    }

    @PostMapping("/userstoprojects")
    public ResponseEntity<UserWithPassword> deleteUserFromProjectsTeam(@RequestBody DeletedUserFromTeam deletedUserFromTeam) {
        UserWithPassword userWithPasswordToDeleteFromTeam = deletedUserFromTeam.getUserWithPasswordToDeleteFromTeam();
        BigInteger projectId = deletedUserFromTeam.getProjectId();

        if (userWithPasswordToDeleteFromTeam == null || projectId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean operationResult = projectService.deleteUserFromTeam(userWithPasswordToDeleteFromTeam, projectId);

        if (!operationResult) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userWithPasswordToDeleteFromTeam, HttpStatus.OK);

    }

    public static Iterable<User> convertUsersToUsersDTO(List<UserWithPassword> userWithPasswords){
        return userWithPasswords.stream().map(user -> User.of(user)).collect(Collectors.toList());
    }
}
