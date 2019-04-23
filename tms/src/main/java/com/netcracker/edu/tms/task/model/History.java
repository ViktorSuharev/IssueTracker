package com.netcracker.edu.tms.task.model;

import com.netcracker.edu.tms.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "task_id", nullable = false)
    private BigInteger taskId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public History(BigInteger taskId, String comment, User author, Date created) {
        this.taskId = taskId;
        this.comment = comment;
        this.author = author;
        this.created = created;
    }
}