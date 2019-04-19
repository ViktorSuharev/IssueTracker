package com.netcracker.edu.tms.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.netcracker.edu.tms.user.model.User;

@Data
@AllArgsConstructor
public class ProjectWithCreator {
    private Project project;
    private User owner;
}
