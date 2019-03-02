package com.netcracker.edu.tms.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}
