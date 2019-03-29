package com.netcracker.edu.tms.ui;

import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.payload.LoginRequest;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginRegisterRestController {
    private UserService userService;

    @Autowired
    public LoginRegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtAuthenticationResponse jwt = userService.login(loginRequest);
            return ResponseEntity.ok(jwt);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
