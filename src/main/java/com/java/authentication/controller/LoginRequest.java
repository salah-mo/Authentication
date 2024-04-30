package com.java.authentication.controller;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String test, String password) {
        this.username = test;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}