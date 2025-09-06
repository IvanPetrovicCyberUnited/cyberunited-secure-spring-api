package com.cyberunited.secureapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if ("TRACE".equals(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        if ("POST".equals(request.getMethod())) {
            String ct = request.getContentType();
            if (ct == null || !MediaType.APPLICATION_JSON_VALUE.equals(ct)) {
                response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
