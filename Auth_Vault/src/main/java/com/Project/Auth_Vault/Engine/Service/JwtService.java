package com.Project.Auth_Vault.Engine.Service;

import com.Project.Auth_Vault.Engine.Entity.Role;
import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import com.Project.Auth_Vault.GlobalException.InValidTokenException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    String secretKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * DEVELOPER token — issued at /developer/login.
     * Carries role="DEVELOPER" (or whatever the user's Role enum resolves to).
     * NOT scoped to any specific client app — should never unlock a
     * protected end-user route in a developer's app.
     */
    public String generateToken(SignupEntity user) {

        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::name)
                .orElse("USER");

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * CLIENT (end-user) token — issued at the client-facing register/login
     * endpoint, scoped to a specific clientId/app. This is the ONLY token
     * type that should pass validation for a "protected" route.
     */
    public String generateClientToken(SignupEntity user, String clientId) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", "CLIENT")
                .claim("clientId", clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) throws InValidTokenException {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new InValidTokenException("Token invalid or tampered");
        }
    }

    public String extractRole(String token) throws InValidTokenException {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("role", String.class);
        } catch (JwtException e) {
            throw new InValidTokenException("Token invalid or tampered");
        }
    }

    /**
     * Returns null if the token has no clientId claim (e.g. a developer token).
     * Callers must handle null explicitly rather than assuming a match.
     */
    public String extractClientId(String token) throws InValidTokenException {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("clientId", String.class);
        } catch (JwtException e) {
            throw new InValidTokenException("Token invalid or tampered");
        }
    }

    public boolean validateToken(String token, String username) throws InValidTokenException {
        String extractedUserName = extractUsername(token);
        boolean isUsernameMatch = extractedUserName.equals(username);
        boolean isNotExpired = !isTokenExpired(token);
        return isUsernameMatch && isNotExpired;
    }

    public boolean isTokenExpired(String token) throws InValidTokenException {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            throw new InValidTokenException("Token Invalid or Expired");
        }
    }
}