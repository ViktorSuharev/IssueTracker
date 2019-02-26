package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private java.util.Date deadline;
    @Column(name = "creation_date")
    private java.util.Date creationDate;
    @Column(name = "reported_id")
    private BigInteger reportedId;
    @Column(name = "assignee_id")
    private BigInteger assigneeId;
    @Column(name = "status_id")
    private BigInteger statusId;
    @Column(name = "modification_date")
    private java.util.Date modificationDate;
    @Column(name = "project_id")
    private BigInteger projectId;
    @Column(name = "priority_id")
    private BigInteger priorityId;

}
