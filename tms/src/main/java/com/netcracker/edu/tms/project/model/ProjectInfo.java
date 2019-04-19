package com.netcracker.edu.tms.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectInfo {
    Project project;
    List<ProjectMember> team;
}
