package ru.kashtanov.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kashtanov.auth_service.dto.ValidateTokenDto;
import ru.kashtanov.auth_service.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token == null) {
            log.debug("No JWT token found in request: {}", request.getRequestURI());
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "No JWT found in request");
            return;
        }
        ValidateTokenDto dto = jwtService.validateToken(token);
        if (!dto.isValid()) {
            sendError(response, dto.getStatus(), dto.getErrorName());
            return;
        }
        authenticate(token);
        filterChain.doFilter(request, response);
    }


    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }


    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7).trim();
        if (token.isBlank()) {
            return null;
        }
        return token;
    }


    private void authenticate(String token) {
        String userName = jwtService.extractUsername(token);
        List<String> roles = jwtService.extractRoles(token);
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
