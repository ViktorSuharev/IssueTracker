package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.DeletedUserFromTeam;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.Task;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> ret = new LinkedList<>();

        ret.add(new User(
                BigInteger.valueOf(1),
                "stubUser11111111111111111111111111111111111111111",
                "stubPassword1",
                "stubEmail1",
                BigInteger.valueOf(1)));
        ret.add(new User(
                BigInteger.valueOf(2),
                "stubUser2",
                "stubPassword2",
                "stubEmail22222222222222222222222222222222222222222222222",
                BigInteger.valueOf(2)));
        ret.add(new User(
                BigInteger.valueOf(3),
                "stubUser3",
                "stubPassword3",
                "stubEmail3",
                BigInteger.valueOf(2)));
        ret.add(new User(
                BigInteger.valueOf(4),
                "stubUser4",
                "stubPassword4",
                "stubEmail4",
                BigInteger.valueOf(3)));
        ret.add(new User(
                BigInteger.valueOf(5),
                "stubUser5",
                "stubPassword5",
                "stubEmail5",
                BigInteger.valueOf(3)));

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<Task>> getUsersTasks(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.getTasksByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<Project>> getProjectsByCreatorId(@PathVariable(name = "id", required = true) BigInteger userId) {
        return new ResponseEntity<>(projectService.findProjectsByCreatorId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id", required = true) BigInteger creatorId) {
        return new ResponseEntity<>(new User(
                BigInteger.valueOf(1L),
                "StubUserCreator",
                "12345",
                "stubUserCreator@mail.ru",
                BigInteger.valueOf(1)), HttpStatus.OK);
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
