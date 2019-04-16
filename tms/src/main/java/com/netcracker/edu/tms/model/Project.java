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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "creator_id", nullable = false)
    private BigInteger creatorId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    //List<Pair<User, Role>> team

    public Project(BigInteger creatorId, String name) {
        this.creatorId = creatorId;
        this.name = name;
    }
}
