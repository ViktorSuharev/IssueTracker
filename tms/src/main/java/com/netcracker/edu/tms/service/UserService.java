package com.netcracker.edu.tms.service;


import com.netcracker.edu.tms.model.Role;
import com.netcracker.edu.tms.model.User;

public interface UserService {
    User register(User user);

    User attachRole(User user, Role role);

    User detachRole(User user, Role role);
}
