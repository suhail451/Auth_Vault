package com.Project.Auth_Vault.Engine.Service;


import com.Project.Auth_Vault.Engine.DTO.LoginRequest;
import com.Project.Auth_Vault.Engine.DTO.LoginResponse;
import com.Project.Auth_Vault.Engine.DTO.MeResponse;
import com.Project.Auth_Vault.Engine.DTO.RefreshRequest;
import com.Project.Auth_Vault.Engine.Entity.RefreshTokenEntity;
import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import com.Project.Auth_Vault.Engine.GlobalException.InValidCredentialException;
import com.Project.Auth_Vault.Engine.GlobalException.InValidTokenException;
import com.Project.Auth_Vault.Engine.GlobalException.UserNotFoundException;
import com.Project.Auth_Vault.Engine.Repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    final LoginRepository loginRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
     final RefreshTokenService refreshTokenService;
     final BlockListService blockListService;


    public AuthService(LoginRepository loginRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService, BlockListService blockListService) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.blockListService = blockListService;
    }


    public LoginResponse login(LoginRequest loginRequest) throws InValidCredentialException {

        SignupEntity user = loginRepository.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User nahi mil raha bhi"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InValidCredentialException("Invalid credentials");

        }

        String accessToken = jwtService.generateToken(user);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken.getToken());

    }
    public LoginResponse refresh(RefreshRequest request) throws InValidTokenException {
        RefreshTokenEntity refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
        String newAccessToken = jwtService.generateToken(refreshToken.getUser());
        return new LoginResponse(newAccessToken, refreshToken.getToken());
    }

    public void logout(String accessToken,String refreshToken) {

        blockListService.blacklistToken(accessToken);
        refreshTokenService.deleteToken(refreshToken);

    }

    public MeResponse getMe(String username) {

        // Step 1 — database se user dhundo username se
        SignupEntity user = loginRepository.findByusername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Step 2 — username aur roles return karo
        return new MeResponse(
                user.getUsername(),
                user.getRoles()
        );
    }


}
