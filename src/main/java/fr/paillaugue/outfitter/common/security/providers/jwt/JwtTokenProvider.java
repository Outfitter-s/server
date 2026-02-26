package fr.paillaugue.outfitter.common.security.providers.jwt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.UUID;

@ConfigurationProperties(prefix = "authentication.jwt")
record JwtTokenProviderConfig(@NotBlank String secret, @Positive long expiration) {
}

@Component
@EnableConfigurationProperties(JwtTokenProviderConfig.class)
public class JwtTokenProvider {

    private final String JWT_SECRET;
    private final long JWT_EXPIRATION;

    public JwtTokenProvider(JwtTokenProviderConfig config) {
        this.JWT_SECRET = config.secret();
        this.JWT_EXPIRATION = config.expiration();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}
