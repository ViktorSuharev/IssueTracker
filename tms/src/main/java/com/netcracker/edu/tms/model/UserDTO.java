package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class UserDTO {
    private BigInteger id;
    private String email;
    private String name;

    public static UserDTO of(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName()
        );
    }
}
