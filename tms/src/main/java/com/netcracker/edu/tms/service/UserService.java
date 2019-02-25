package com.netcracker.edu.tms.service;


import com.netcracker.edu.tms.model.User;

public interface UserService {
    boolean register(User user);

    boolean checkLogin(String email, String passsword);
}
