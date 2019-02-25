package com.netcracker.edu.tms.dao.impl;

import com.netcracker.edu.tms.dao.RoleDao;
import com.netcracker.edu.tms.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean create(String name) {
        entityManager.persist(new Role(name));
        return true;
    }

    @Transactional
    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        String queryString = "SELECT r FROM Role r " +
                "WHERE r.name = :name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);

        return (Role) query.getSingleResult();

    }

    @Transactional
    @Override
    public boolean update(Role role) {
        Role r = getRoleById(role.getId());
        if(r == null)
            return false;

        Role.clone(role, r);
        entityManager.flush();

        return true;
    }

    @Transactional
    @Override
    public boolean delete(Role role) {
        entityManager.remove(role);

        return true;
    }

    @Override
    public Collection<Role> getAllRoles() {
        String queryString = "SELECT r FROM Role r";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
}
