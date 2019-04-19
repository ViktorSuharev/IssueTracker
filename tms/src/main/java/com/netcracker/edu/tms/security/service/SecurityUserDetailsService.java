package com.netcracker.edu.tms.security.service;

import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserWithPassword userWithPassword = userRepository.findByEmail(email);

        if (userWithPassword == null) {
            throw new UsernameNotFoundException("UserWithPassword not found by name : " + email);
        }

        return UserPrincipal.create(userWithPassword);
    }
}