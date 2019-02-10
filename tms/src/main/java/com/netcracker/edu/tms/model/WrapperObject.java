package com.netcracker.edu.tms.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WrapperObject {
    Project newProject;
    List<User> addedUsers;
}
