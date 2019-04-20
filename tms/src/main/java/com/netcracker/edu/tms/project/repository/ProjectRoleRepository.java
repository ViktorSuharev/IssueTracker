package com.netcracker.edu.tms.project.repository;

import com.netcracker.edu.tms.project.model.ProjectRole;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ProjectRoleRepository extends CrudRepository<ProjectRole, BigInteger> {
    ProjectRole findByName(String name);
}
