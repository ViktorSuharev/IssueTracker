package com.netcracker.edu.tms.dao;

import com.netcracker.edu.tms.model.Role;

import java.util.Collection;

public interface RoleDao {
    boolean create(String name);

    Role getRoleById(int id);

    Role getRoleByName(String name);

    boolean update(Role role);

    boolean delete(Role role);

    Collection<Role> getAllRoles();
}
