package com.cyberunited.secureapi.user;

import java.util.Set;

public record UserInfo(Long id, String username, Set<String> roles) {}
