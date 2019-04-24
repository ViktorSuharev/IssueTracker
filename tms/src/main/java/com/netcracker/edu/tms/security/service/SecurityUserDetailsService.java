package com.netcracker.edu.tms.security.service;

import com.netcracker.edu.tms.security.model.UserPrincipal;
import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.user.repository.UserWithPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private UserWithPasswordRepository userWithPasswordRepository;

    @Autowired
    public SecurityUserDetailsService(UserWithPasswordRepository userWithPasswordRepository) {
        this.userWithPasswordRepository = userWithPasswordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithPassword userWithPassword = userWithPasswordRepository.findByEmail(username);

        if (userWithPassword == null) {
            throw new UsernameNotFoundException("UserWithPassword not found by name : " + username);
        }

        return UserPrincipal.create(userWithPassword);
    }
}