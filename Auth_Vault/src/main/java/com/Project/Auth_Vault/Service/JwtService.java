package com.Project.Auth_Vault.Service;



import com.Project.Auth_Vault.DTO.RefreshRequest;
import com.Project.Auth_Vault.Entity.Role;
import com.Project.Auth_Vault.Entity.SignupEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

//    private final RefreshTokenService refreshTokenService;


    @Value("${jwt.secret}")
    String secretKey;

//    public JwtService(RefreshTokenService refreshTokenService) {
//        this.refreshTokenService = refreshTokenService;
//    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

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




}




