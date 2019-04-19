package com.netcracker.edu.tms.repository;

import com.netcracker.edu.tms.user.model.Role;
import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserWithPasswordRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Ignore
    @Test
    public void testGetUserById() {
        UserWithPassword persisted = new UserWithPassword();
        persisted.setFullName("name");
        persisted.setEmail("email@yandex.ru");


        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        persisted.setRoles(roles);

        BigInteger userId = (BigInteger) entityManager.persistAndGetId(persisted);
        UserWithPassword saved = userRepository.findById(userId).get();

        Assert.assertEquals(getRoles(persisted), getRoles(saved));
    }

    private Set<String> getRoles(UserWithPassword userWithPassword) {
        return userWithPassword.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}