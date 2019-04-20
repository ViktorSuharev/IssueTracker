package com.netcracker.edu.tms.project.repository;

import com.netcracker.edu.tms.project.model.Project;
import com.netcracker.edu.tms.project.model.ProjectMember;
import com.netcracker.edu.tms.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TeamRepository extends CrudRepository<ProjectMember, BigInteger> {
    ProjectMember findByProject(Project project);

    Iterable<ProjectMember> findAllByProject(Project project);

    Iterable<ProjectMember> findAllByUser(User user);

    void deleteAllByProject(Project project);

    void deleteByProjectAndUser(Project project, User user);
}
