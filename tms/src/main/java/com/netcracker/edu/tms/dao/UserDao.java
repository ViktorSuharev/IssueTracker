package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserDao {
    /**
     * Select user from DB by specified id
     * @param userId
     * @return {@link User} or null
     */
    User getUserById(BigInteger userId);

    /**
     * Add new User to database
     * @param user
     * @return true or false depending on operation result
     */
    boolean addUser(User user);

    /**
     * update existent user in database
     * @param user
     * @return true or false depending on operation result
     */
    boolean updateUser(User user);

    /**
     * delete user from database by userId
     * @param userId
     * @return true or false depending on operation result
     */
    boolean deleteUser(User userId);

    /**
     * Select user from DB by specified email
     * @param email
     * @return {@link User} or null
     */
    User getUserByEmail(String email);

    /**
     * Select list of Users from DB by specified secondNames
     * @param fullName
     * @return list of {@link User} or empty list
     */
    List<User> getUsersByFullName(String fullName);

    /**
     * Select all Users from DB
     * @return list of {@link User} or empty list
     */
    List<User> getAllUsers();
}
