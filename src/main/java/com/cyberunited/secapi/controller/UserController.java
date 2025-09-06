package com.cyberunited.secapi.controller;

import com.cyberunited.secapi.model.UserDto;
import com.cyberunited.secapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(HttpServletRequest request) {
        Object idObj = request.getAttribute("userId");
        if (idObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = ((Number) idObj).longValue();
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(new UserDto(u.getId(), u.getUsername())))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> byId(@PathVariable Long id, HttpServletRequest request) {
        Object idObj = request.getAttribute("userId");
        if (idObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long currentId = ((Number) idObj).longValue();
        if (!id.equals(currentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(new UserDto(u.getId(), u.getUsername())))
                .orElse(ResponseEntity.notFound().build());
    }
}
