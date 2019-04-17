package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Column(name = "task_id", unique = true, nullable = false)
    private BigInteger taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

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
    //@Future
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    //@ManyToOne
    private BigInteger reporterId; //should be User foreign key

    //@ManyToOne
    private BigInteger assigneeId; //should be User foreign key

    //@ManyToOne
    private BigInteger projectId; //should be Project foreign key

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority")
    private Priority priority;


}