package com.netcracker.edu.tms.project.model;

import com.netcracker.edu.tms.user.model.User;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    //List<Pair<UserWithPassword, Role>> team

    public Project(BigInteger id, User creator, String name) {
        this.id = id;
        this.creator = creator;
        this.name = name;
    }

    public Project(User creator, String name) {
        this.creator = creator;
        this.name = name;
    }

    public Project(User creator, String name, String description) {
        this.creator = creator;
        this.name = name;
        this.description = description;
    }

    public Project clone(Project source) {
        this.creator = source.creator;
        this.name = source.name;
        this.description = source.description;

        return this;
    }
}
