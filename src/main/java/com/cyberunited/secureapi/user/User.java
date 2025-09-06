package com.cyberunited.secureapi.user;

import java.util.Set;

public record User(Long id, String username, String passwordHash, Set<String> roles) {}
