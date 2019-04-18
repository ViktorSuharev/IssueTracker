package com.netcracker.edu.tms.repository;

import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TaskRepository extends CrudRepository<Task, BigInteger> {
    boolean existsByProjectAndName(Project project, String name);
}
