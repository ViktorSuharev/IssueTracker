package com.netcracker.edu.tms.user.service;


import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.security.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.security.payload.LoginRequest;

import java.math.BigInteger;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    UserWithPassword register(UserWithPassword userWithPassword);

    Iterable<UserWithPassword> getAllUsers();

    UserWithPassword getUserByEmail(String email);

    boolean existsByEmail(String email);

    UserWithPassword getUserByID(BigInteger id);
}
