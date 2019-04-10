package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.DeletedUserFromTeam;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.Task;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.security.UserPrincipal;
import com.netcracker.edu.tms.service.ProjectService;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<User> aboutMe(@AuthenticationPrincipal UserPrincipal currentUser) {
        User user = userService.getUserByEmail(currentUser.getUsername());
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> aboutUser(@PathVariable(name="id") BigInteger id) {
        User user = userService.getUserByID(id);
        return ResponseEntity.ok(user);
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
    public ResponseEntity<User> deleteUserFromProjectsTeam(@RequestBody DeletedUserFromTeam deletedUserFromTeam) {
        User userToDeleteFromTeam = deletedUserFromTeam.getUserToDeleteFromTeam();
        BigInteger projectId = deletedUserFromTeam.getProjectId();

        if (userToDeleteFromTeam == null || projectId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean operationResult = projectService.deleteUserFromTeam(userToDeleteFromTeam, projectId);

        if (!operationResult) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userToDeleteFromTeam, HttpStatus.OK);

    }
}
