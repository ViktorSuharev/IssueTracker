package com.netcracker.edu.tms.service.impl;

import com.netcracker.edu.tms.dao.RoleDao;
import com.netcracker.edu.tms.model.Role;
import com.netcracker.edu.tms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public boolean createRole(String roleName) {
        if(roleDao.getRoleByName(roleName) != null)
            return false;

        return roleDao.create(roleName);
    }

    @Override
    public boolean deleteRole(String roleName) {
        Role role = roleDao.getRoleByName(roleName);

        if(role == null)
            return false;

        return roleDao.delete(role);
    }
}
