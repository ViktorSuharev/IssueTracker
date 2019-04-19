package com.netcracker.edu.tms.security.controller;

import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.security.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.security.payload.LoginRequest;
import com.netcracker.edu.tms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginRegisterRestController {
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public LoginRegisterRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity register(@RequestBody UserWithPassword userWithPassword) {
        try {
            userWithPassword.setPassword(passwordEncoder.encode(userWithPassword.getPassword()));
            userService.register(userWithPassword);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
