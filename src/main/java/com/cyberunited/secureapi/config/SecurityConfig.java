package com.cyberunited.secureapi.config;

import com.cyberunited.secureapi.auth.JwtAuthenticationFilter;
import com.cyberunited.secureapi.auth.JwtUtil;
import com.cyberunited.secureapi.filter.CorrelationIdFilter;
import com.cyberunited.secureapi.filter.RateLimitFilter;
import com.cyberunited.secureapi.filter.RequestValidationFilter;
import com.cyberunited.secureapi.filter.SecurityHeadersFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final CorrelationIdFilter correlationIdFilter;
    private final SecurityHeadersFilter securityHeadersFilter;
    private final RateLimitFilter rateLimitFilter;
    private final RequestValidationFilter requestValidationFilter;

    public SecurityConfig(JwtUtil jwtUtil, CorrelationIdFilter correlationIdFilter,
                          SecurityHeadersFilter securityHeadersFilter, RateLimitFilter rateLimitFilter,
                          RequestValidationFilter requestValidationFilter) {
        this.jwtUtil = jwtUtil;
        this.correlationIdFilter = correlationIdFilter;
        this.securityHeadersFilter = securityHeadersFilter;
        this.rateLimitFilter = rateLimitFilter;
        this.requestValidationFilter = requestValidationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/health").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/admin/stats").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((req, res, e) -> res.sendError(HttpServletResponse.SC_FORBIDDEN))
            )
            .addFilterBefore(requestValidationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(correlationIdFilter, JwtAuthenticationFilter.class)
            .addFilterBefore(securityHeadersFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
