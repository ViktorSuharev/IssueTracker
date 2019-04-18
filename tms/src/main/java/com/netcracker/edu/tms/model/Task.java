package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;

    @Column(name = "due_date")
    @Future
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "modification_date")
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    public Task(String name, String description, Date creationDate, @Future Date dueDate, Date modificationDate, User reporter, User assignee, Project project, Status status, Priority priority) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.modificationDate = modificationDate;
        this.reporter = reporter;
        this.assignee = assignee;
        this.project = project;
        this.status = status;
        this.priority = priority;
    }

    public Task(String name, String description, @Future Date dueDate, User reporter, User assignee, Project project, Priority priority) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.reporter = reporter;
        this.assignee = assignee;
        this.project = project;
        this.priority = priority;
    }
}