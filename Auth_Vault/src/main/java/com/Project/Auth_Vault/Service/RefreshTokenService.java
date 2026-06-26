package com.Project.Auth_Vault.Service;


import com.Project.Auth_Vault.Entity.RefreshTokenEntity;
import com.Project.Auth_Vault.Entity.SignupEntity;
import com.Project.Auth_Vault.Repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository ) {
        this.refreshTokenRepository = refreshTokenRepository;

    }

    // Naya refresh token banao aur DB mein save karo
    public RefreshTokenEntity createRefreshToken(SignupEntity user) {
        RefreshTokenEntity token = new RefreshTokenEntity();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(604_800_000)); // 7 days
        token.setRevoked(false);
        return refreshTokenRepository.save(token);
    }

    // Token valid hai ya nahi check karo
    public RefreshTokenEntity verifyExpiration(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.isRevoked() || refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired or revoked");
        }
        return refreshToken;
    }

    // Logout par token revoke karo
    public void revokeToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}

