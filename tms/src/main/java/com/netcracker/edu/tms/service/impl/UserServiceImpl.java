package com.netcracker.edu.tms.service.impl;

import com.netcracker.edu.tms.model.Role;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.payload.LoginRequest;
import com.netcracker.edu.tms.repository.RoleRepository;
import com.netcracker.edu.tms.repository.UserRepository;
import com.netcracker.edu.tms.security.JwtTokenProvider;
import com.netcracker.edu.tms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Transactional
    @Override
    public User register(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            String warn = String.format("%s already registered", user.getEmail());
            log.warn(warn);
            throw new IllegalArgumentException(warn);
        }

        user.getRoles().add(getOrCreateRole("USER"));

        return userRepository.save(user);
    }

    @Override
    public User attachRole(User user, Role role) {
        if(role.equals(getOrCreateRole("user")))
            throw new IllegalArgumentException("USER role isn't detachable");

        user = userRepository.findById(user.getId()).get();
        role = roleRepository.findById(role.getId()).get();

        if(user == null || role == null)
            throw new IllegalArgumentException("Role or user is null");

        if(!user.getRoles().add(role))
            throw new IllegalArgumentException(
                    String.format("'%s' already has role '%s'", user.getEmail(), role.getName()));

        user = userRepository.save(user);
        return user;
    }

    @Override
    public User detachRole(User user, Role role) {
        return user;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    private Role getOrCreateRole(String name){
        Role role = roleRepository.findByName("user");
        if(role != null)
            return role;

        return roleRepository.save(new Role(name));
    }
}