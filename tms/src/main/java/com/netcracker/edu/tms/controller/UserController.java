package com.netcracker.edu.tms.controller;

import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.payload.LoginRequest;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final String USER_ATTR = "user";
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String login(Model model) {
        model.addAttribute(USER_ATTR, new LoginRequest());
        return "signin";
    }

    @PostMapping("/signin")
    public ResponseEntity login(Model model, LoginRequest loginRequest) {
        try {
//            loginRequest.setPassword(loginRequest.getPassword()));
            JwtAuthenticationResponse jwt = userService.login(loginRequest);
            return ResponseEntity.ok(jwt);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("redirect:/error.html");
        }
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute(USER_ATTR, new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String register(Model model, User user) {
        try {
            userService.register(user);
            return "redirect:/success.html";
        } catch (IllegalArgumentException ex) {
            return "redirect:/error.html";
        }
    }
}
