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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public User(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }

    public static void clone(User source, User destination){
        destination.id = source.id;
        destination.fullName = source.fullName;
        destination.password = source.password;
        destination.email = source.email;
        destination.role = source.role;
    }
}
