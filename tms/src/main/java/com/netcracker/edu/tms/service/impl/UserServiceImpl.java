package com.netcracker.edu.tms.service.impl;

import com.netcracker.edu.tms.model.Role;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.repository.RoleRepository;
import com.netcracker.edu.tms.repository.UserRepository;
import com.netcracker.edu.tms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public User register(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            String warn = String.format("%s already registered", user.getEmail());
            log.warn(warn);
            throw new IllegalArgumentException(warn);
        }

        user.getRoles().add(getOrCreateRole("user"));

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

    private Role getOrCreateRole(String name){
        Role role = roleRepository.findByName("user");
        if(role != null)
            return role;

        return roleRepository.save(new Role(name));
    }
}