package com.netcracker.edu.tms.user.repository;

import com.netcracker.edu.tms.user.model.UserWithPassword;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface UserWithPasswordRepository extends CrudRepository<UserWithPassword, BigInteger>{
    UserWithPassword findByEmail(String email);

    boolean existsByEmail(String email);
}
