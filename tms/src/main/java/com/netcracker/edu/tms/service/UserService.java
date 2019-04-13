package com.netcracker.edu.tms.service;


import com.netcracker.edu.tms.model.Role;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.payload.LoginRequest;

import java.math.BigInteger;
import java.util.Collection;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    User register(User user);

    Iterable<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserByID(BigInteger id);
}
