package com.netcracker.edu.tms.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.netcracker.edu.tms.user.model.UserWithPassword;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeletedUserFromTeam {
    BigInteger projectId;
    UserWithPassword userWithPasswordToDeleteFromTeam;
}
