package com.cyberunited.secureapi.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                var claims = jwtUtil.parser().parseSignedClaims(token).getPayload();
                String username = claims.getSubject();
                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("roles", List.class);
                var auth = new UsernamePasswordAuthenticationToken(username, null,
                        roles.stream().map(SimpleGrantedAuthority::new).toList());
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.setAttribute("userId", claims.get("id", Long.class));
            } catch (Exception ignored) {
            }
        }
        chain.doFilter(request, response);
    }
}
