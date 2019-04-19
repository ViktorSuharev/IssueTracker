package com.netcracker.edu.tms.user.model;

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

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "fullName")
    private String name;

    public static User of(UserWithPassword userWithPassword) {
        return new User(
                userWithPassword.getId(),
                userWithPassword.getEmail(),
                userWithPassword.getFullName()
        );
    }
}
