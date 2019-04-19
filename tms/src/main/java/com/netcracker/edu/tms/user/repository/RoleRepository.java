package com.netcracker.edu.tms.user.repository;

import com.netcracker.edu.tms.user.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface RoleRepository extends CrudRepository<Role, BigInteger> {

    Role findByName(String name);
}
