package com.Project.Auth_Vault.Service;


import com.Project.Auth_Vault.DTO.LoginRequest;
import com.Project.Auth_Vault.DTO.LoginResponse;
import com.Project.Auth_Vault.DTO.RefreshRequest;
import com.Project.Auth_Vault.Entity.RefreshTokenEntity;
import com.Project.Auth_Vault.Entity.SignupEntity;
import com.Project.Auth_Vault.Repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    final LoginRepository loginRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(LoginRepository loginRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }


    public LoginResponse login(LoginRequest loginRequest) {

        SignupEntity user = loginRepository.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");

        }

        String accessToken = jwtService.generateToken(user);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken.getToken());

    }
    public LoginResponse refresh(RefreshRequest request) {
        RefreshTokenEntity refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
        String newAccessToken = jwtService.generateToken(refreshToken.getUser());
        return new LoginResponse(newAccessToken, refreshToken.getToken());
    }

    public void logout(RefreshRequest request) {
        refreshTokenService.deleteToken(request.getRefreshToken());
    }


}
