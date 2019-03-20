package com.netcracker.edu.tms.dao.impl;

import com.netcracker.edu.tms.dao.UserDao;
import com.netcracker.edu.tms.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

//todo вынести запросы в константы
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserById(BigInteger userId) {
        return entityManager.find(User.class, userId);
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        entityManager.persist(user);
        return true;
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        User us = getUserById(user.getId());
        if(us == null)
            return false;

        User.clone(user, us);
        entityManager.flush();

        return true;
    }

    @Transactional
    @Override
    public boolean deleteUser(User user) {
        entityManager.remove(user);

        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        String queryString = "SELECT u FROM User u " +
                "WHERE u.email = :email";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("email", email);

        return (User)query.getSingleResult();
    }

    @Override
    public List<User> getUsersByFullName(String fullName) {
        String queryString = "SELECT u FROM User u " +
                "WHERE u.fullname = :fullName";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("fullName", fullName);

        return query.getResultList();
    }

    @Override
    public List<User> getAllUsers() {
        String queryString = "SELECT u FROM User u";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
}
