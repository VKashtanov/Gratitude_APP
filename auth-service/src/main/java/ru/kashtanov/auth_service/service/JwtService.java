package ru.kashtanov.auth_service.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.kashtanov.auth_service.dto.ValidateTokenDto;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Viktor Кashtanov
 */
@Service
public class JwtService {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-access-expiration-milliseconds}")
    private Long accessExpirationDate;

    @Value("${app.jwt-refresh-expiration-milliseconds}")
    private Long refreshExpirationDate;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Instant now = Instant.now();
        Instant expirationAccessTokenDate = now.plusMillis(accessExpirationDate);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationAccessTokenDate))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        Instant expirationRefreshTokenDate = now.plusMillis(refreshExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationRefreshTokenDate))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public ValidateTokenDto validateToken(String token) {
        var dto = new ValidateTokenDto();
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            dto.setValid(true);
            return dto;
        } catch (ExpiredJwtException e) {
            dto.setValid(false);
            dto.setErrorName("Token is empty");
            dto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return dto;

        } catch (UnsupportedJwtException e) {
            dto.setValid(false);
            dto.setErrorName("Unsupported token");
            dto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return dto;

        } catch (MalformedJwtException e) {
            dto.setValid(false);
            dto.setErrorName("Malformed token");
            dto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return dto;
        } catch (SignatureException e) {
            dto.setValid(false);
            dto.setErrorName("Signature invalid");
            dto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return dto;

        } catch (IllegalArgumentException e) {
            dto.setValid(false);
            dto.setErrorName("Arguments invalid");
            dto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return dto;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // converts string to crypto graphic key
    }
}
