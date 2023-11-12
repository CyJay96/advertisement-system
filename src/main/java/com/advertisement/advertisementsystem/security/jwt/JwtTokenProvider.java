package com.advertisement.advertisementsystem.security.jwt;

import com.advertisement.advertisementsystem.exception.TokenExpirationException;
import com.advertisement.advertisementsystem.model.entity.Role;
import com.advertisement.advertisementsystem.model.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.expired}")
    private Long validityInMs;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String USER_ROLES_FIELD = "roles";

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private final UserDetailsService userDetailsService;

    public String createToken(final User user) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim(USER_ROLES_FIELD, user.getRoles().stream()
                        .map(Role::getName)
                        .toList())
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }
        return null;
    }

    public boolean validateToken(final String token) {
        try {
            final Date expirationDate = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            return expirationDate.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenExpirationException();
        }
    }
}
