package com.netcracker.edu.tms.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.netcracker.edu.tms.user.model.User;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private BigInteger id;
    private String name;
    private String description;
    private User creator;
}
