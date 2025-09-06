package com.cyberunited.secapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.web.filter.OncePerRequestFilter;

public class CspNonceFilter extends OncePerRequestFilter {
    public static final String NONCE_ATTR = "cspNonce";
    private final SecureRandom random = new SecureRandom();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        byte[] nonceBytes = new byte[16];
        random.nextBytes(nonceBytes);
        String nonce = Base64.getEncoder().encodeToString(nonceBytes);
        request.setAttribute(NONCE_ATTR, nonce);
        response.setHeader("Content-Security-Policy", "default-src 'none'; script-src 'self' 'nonce-" + nonce + "'; object-src 'none'; base-uri 'none'; frame-ancestors 'none'");
        filterChain.doFilter(request, response);
    }
}
