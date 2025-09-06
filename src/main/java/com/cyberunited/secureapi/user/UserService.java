package com.cyberunited.secureapi.user;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService() {
        users.put("user", new User(1L, "user", encoder.encode("password"), Set.of("ROLE_USER")));
        users.put("admin", new User(2L, "admin", encoder.encode("password"), Set.of("ROLE_ADMIN")));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Optional<User> findById(Long id) {
        return users.values().stream().filter(u -> u.id().equals(id)).findFirst();
    }
}
