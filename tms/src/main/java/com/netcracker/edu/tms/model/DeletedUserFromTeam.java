package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeletedUserFromTeam {
    BigInteger projectId;
    User userToDeleteFromTeam;
}
