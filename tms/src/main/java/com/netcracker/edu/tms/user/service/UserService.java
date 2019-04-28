package com.netcracker.edu.tms.user.service;

import com.netcracker.edu.tms.security.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.security.payload.LoginRequest;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    User register(UserWithPassword userWithPassword);

    Iterable<User> getAllUsers();

    User getUserByEmail(String email);

    boolean existsByEmail(String email);

    User getUserById(BigInteger id);
}
