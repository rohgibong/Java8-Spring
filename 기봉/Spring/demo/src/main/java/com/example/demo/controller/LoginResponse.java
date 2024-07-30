package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String sessionId; // optional, only when login is successful

    public LoginResponse(String message) {
        this.message = message;
    }
}
