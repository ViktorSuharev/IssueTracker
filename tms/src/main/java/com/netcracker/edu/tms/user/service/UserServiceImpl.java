package com.netcracker.edu.tms.user.service;

import com.netcracker.edu.tms.user.model.Role;
import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.security.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.security.payload.LoginRequest;
import com.netcracker.edu.tms.user.repository.RoleRepository;
import com.netcracker.edu.tms.user.repository.UserRepository;
import com.netcracker.edu.tms.security.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    private static final String USER_ROLE_NAME = "ROLE_USER";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;

        createRoleIfNotExist(USER_ROLE_NAME);
    }

    private void createRoleIfNotExist(String roleName){
        Role role = roleRepository.findByName(roleName);

        if (role != null)
            return;

        role = new Role(roleName);
        roleRepository.save(role);
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Transactional
    @Override
    public UserWithPassword register(UserWithPassword userWithPassword) {
        if(userRepository.findByEmail(userWithPassword.getEmail()) != null) {
            String warn = String.format("%s already registered", userWithPassword.getEmail());
            log.warn(warn);
            throw new IllegalArgumentException(warn);
        }

        userWithPassword.getRoles().add(roleRepository.findByName(USER_ROLE_NAME));

        return userRepository.save(userWithPassword);
    }

    @Override
    public Iterable<UserWithPassword> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserWithPassword getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserWithPassword getUserByID(BigInteger id) {
        return userRepository.findById(id).get();
    }
}