package com.netcracker.edu.tms.controller;

import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;
    private static final String USER_ATTR = "user";
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String register(Model model){
        model.addAttribute(USER_ATTR, new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String register(Model model, User user){
        if(userService.register(user))
            return "redirect:/success.html";
        return "redirect:/error.html";
    }
}
