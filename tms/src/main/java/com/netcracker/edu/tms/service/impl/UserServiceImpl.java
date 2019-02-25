package com.netcracker.edu.tms.service.impl;

import com.netcracker.edu.tms.dao.UserDao;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public boolean register(User user) {
        return !emailExists(user.getEmail())
                && userDao.addUser(user);
    }

    private boolean emailExists(String email){
        return userDao.getUserByEmail(email) != null;
    }

    @Transactional
    public boolean checkLogin(String email, String password) {
        User user = userDao.getUserByEmail(email);

        return user != null && user.getPassword().equals(password);
    }
}
