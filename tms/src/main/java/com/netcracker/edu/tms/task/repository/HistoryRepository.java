package com.netcracker.edu.tms.task.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.task.model.History;
import com.netcracker.edu.tms.task.model.Task;
import com.netcracker.edu.tms.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface HistoryRepository extends CrudRepository<History, BigInteger> {
    Iterable<History> findAllByTaskId(BigInteger taskId);
}
