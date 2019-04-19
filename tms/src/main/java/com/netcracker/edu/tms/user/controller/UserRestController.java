package com.netcracker.edu.tms.user.controller;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.service.ProjectService;
import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private UserService userService;
    private ProjectService projectService;

    @Autowired
    public UserRestController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
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
    public ResponseEntity<User> aboutUser(@PathVariable(name = "id") BigInteger id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class DeletedUserFromTeam {
        BigInteger projectId;
        User userToDeleteFromTeam;
    }
}
