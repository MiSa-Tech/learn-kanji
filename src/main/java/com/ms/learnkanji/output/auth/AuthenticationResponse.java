package com.ms.learnkanji.output.auth;

public class AuthenticationResponse {
    private String jwt;
    private String id;
    private String username;
    private String role;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, String id, String username, String role) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
