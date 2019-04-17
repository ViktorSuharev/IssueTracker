package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private BigInteger id;
    private String name;
    private String description;
    private UserDTO creator;
}
