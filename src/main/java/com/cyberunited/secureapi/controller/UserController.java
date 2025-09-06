package com.cyberunited.secureapi.controller;

import com.cyberunited.secureapi.user.UserInfo;
import com.cyberunited.secureapi.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HttpServletRequest request;

    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfo> me(Authentication auth) {
        return userService.findByUsername(auth.getName())
                .map(u -> ResponseEntity.ok(new UserInfo(u.id(), u.username(), u.roles())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> byId(@PathVariable Long id, Authentication auth) {
        Long uid = (Long) request.getAttribute("userId");
        if (uid == null || !uid.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(new UserInfo(u.id(), u.username(), u.roles())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
