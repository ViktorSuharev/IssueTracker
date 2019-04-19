package com.netcracker.edu.tms.project.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ProjectRepository extends CrudRepository<Project, BigInteger> {

    Project findByName(String name);

    Iterable<Project> findAllByCreator(User creator);
}
