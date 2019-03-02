package com.netcracker.edu.tms.security;

import com.netcracker.edu.tms.dao.UserDao;
import com.netcracker.edu.tms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public SecurityUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found by name : " + email);
        }

        return UserPrincipal.create(user);
    }
}