package com.netcracker.edu.tms.service.impl;

import com.netcracker.edu.tms.dao.UserDao;
import com.netcracker.edu.tms.model.User;
import com.netcracker.edu.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

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
        try {
            userDao.getUserByEmail(email);
            return true;
        } catch (EmptyResultDataAccessException | NoResultException ex){
            return false;
        }
    }

    @Transactional
    public boolean checkLogin(String email, String password) {
        User user = userDao.getUserByEmail(email);

        return user != null && user.getPassword().equals(password);
    }
}
