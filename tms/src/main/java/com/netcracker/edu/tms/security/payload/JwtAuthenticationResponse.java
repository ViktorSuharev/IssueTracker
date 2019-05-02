package com.netcracker.edu.tms.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
}
