package com.Project.Auth_Vault.Engine.Service;


import com.Project.Auth_Vault.Engine.Entity.RefreshTokenEntity;
import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import com.Project.Auth_Vault.Engine.Repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.Project.Auth_Vault.GlobalException.InValidTokenException;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository ) {
        this.refreshTokenRepository = refreshTokenRepository;

    }

    public RefreshTokenEntity createRefreshToken(SignupEntity user) {
        RefreshTokenEntity token = new RefreshTokenEntity();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(604_800_000)); // 7 days
        token.setRevoked(false);
        return refreshTokenRepository.save(token);
    }

    public RefreshTokenEntity verifyExpiration(String token) throws InValidTokenException {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InValidTokenException("Refresh token not found"));

        if (refreshToken.isRevoked() || refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new InValidTokenException("Refresh token expired or revoked");
        }
        return refreshToken;
    }

    // Logout par token revoke karo
    public void revokeToken(String token) throws InValidTokenException {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InValidTokenException("Refresh token not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}

