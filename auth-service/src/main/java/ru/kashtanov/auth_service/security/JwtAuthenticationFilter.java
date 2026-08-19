package ru.kashtanov.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kashtanov.auth_service.dto.ValidateTokenDto;
import ru.kashtanov.auth_service.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */

// STEP_1  DispatcherServlet accept HTTP request and sends it to the filter chain
// STEP_2  JwtAuthenticationFilter intercepts request before Controller
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // STEP_3 Extract Token from the HEADER
        String token = extractToken(request);
        if (token == null) {
            log.debug("No JWT token found in request: {}", request.getRequestURI());
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "No JWT found in request");
            return;
        }
        // STEP_4 validating Token
        ValidateTokenDto dto = jwtService.validateToken(token);
        if (!dto.isValid()) {
            sendError(response, dto.getStatus(), dto.getErrorName());
            return;
        }
        // we remember a user for the session and save it in context,
        // for each request a new SecurityContext is created
        setAuthenticationToContext(token);

        // STEP_8 TRANSFER REQUEST FURTHER
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


    private void setAuthenticationToContext(String token) {
        // STEP_5 EXTRACTING DATA
        String userName = jwtService.extractUsername(token);
        List<String> roles = jwtService.extractRoles(token);
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        // STEP_6 CREATING WRAPPER CLASS
        var authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);

        // it is a container that lives within session, exists in one Thread, and dies when http request is done
        // STEP_7 SAVING IN SECURITY_CONTEXT, IN THREAD LOCAL
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
