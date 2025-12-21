package com.eCommerce.authService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String message;

    // Constructor for backward compatibility
    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
