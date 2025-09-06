package com.cyberunited.secapi.model;

import java.util.Set;

public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final Set<String> roles;

    public User(Long id, String username, String password, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Set<String> getRoles() { return roles; }
}
