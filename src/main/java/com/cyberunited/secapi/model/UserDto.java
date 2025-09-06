package com.cyberunited.secapi.model;

public class UserDto {
    private final Long id;
    private final String username;

    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
}
