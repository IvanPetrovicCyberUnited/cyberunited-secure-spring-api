package com.cyberunited.secureapi.controller;

import com.cyberunited.secureapi.auth.JwtUtil;
import com.cyberunited.secureapi.auth.LoginRequest;
import com.cyberunited.secureapi.auth.TokenResponse;
import com.cyberunited.secureapi.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return userService.findByUsername(req.username())
                .filter(u -> encoder.matches(req.password(), u.passwordHash()))
                .map(u -> ResponseEntity.ok(new TokenResponse(jwtUtil.generateToken(u.id(), u.username(), u.roles()))))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
