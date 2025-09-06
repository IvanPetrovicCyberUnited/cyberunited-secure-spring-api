package com.cyberunited.secapi.service;

import com.cyberunited.secapi.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    private final Map<Long, User> usersById = new ConcurrentHashMap<>();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService() {
        User user = new User(1L, "user", encoder.encode("password"), Set.of("ROLE_USER"));
        User admin = new User(2L, "admin", encoder.encode("password"), Set.of("ROLE_ADMIN"));
        usersByUsername.put(user.getUsername(), user);
        usersByUsername.put(admin.getUsername(), admin);
        usersById.put(user.getId(), user);
        usersById.put(admin.getId(), admin);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(usersById.get(id));
    }

    public boolean checkPassword(User user, String rawPassword) {
        return encoder.matches(rawPassword, user.getPassword());
    }
}
