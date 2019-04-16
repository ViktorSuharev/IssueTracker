package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_project")
public class UsersToProjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "user_id", nullable = false)
    private BigInteger userId;

    @Column(name = "project_id", nullable = false)
    private BigInteger projectId;

    @Column(name = "role", nullable = false)
    private String role;

    public UsersToProjects(BigInteger userId, BigInteger projectId, String role) {
        this.userId = userId;
        this.projectId = projectId;
        this.role = role;
    }
}
