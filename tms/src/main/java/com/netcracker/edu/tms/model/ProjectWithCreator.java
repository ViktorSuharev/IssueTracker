package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectWithCreator {
    private Project project;
    private UserDTO owner;
}
