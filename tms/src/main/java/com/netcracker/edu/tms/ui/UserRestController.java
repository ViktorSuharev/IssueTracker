package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.*;
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
import java.util.Collection;
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
    @GetMapping("/all")
    public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        List users = StreamSupport.stream(userService.getAllUsers().spliterator(), false).collect(Collectors.toList());
        return new ResponseEntity<>(convertUsersToUsersDTO(users), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> aboutMe(@AuthenticationPrincipal UserPrincipal currentUser) {
        User user = userService.getUserByEmail(currentUser.getUsername());
        return ResponseEntity.ok(UserDTO.of(user));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> aboutUser(@PathVariable(name="id") BigInteger id) {
        User user = userService.getUserByID(id);
        return ResponseEntity.ok(UserDTO.of(user));
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

    public static Iterable<UserDTO> convertUsersToUsersDTO(List<User> users){
        return users.stream().map(user -> UserDTO.of(user)).collect(Collectors.toList());
    }
}
