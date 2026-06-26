package com.Project.Auth_Vault.Service;


import com.Project.Auth_Vault.DTO.LoginRequest;
import com.Project.Auth_Vault.DTO.LoginResponse;
import com.Project.Auth_Vault.Entity.SignupEntity;
import com.Project.Auth_Vault.Repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    final LoginRepository loginRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;

    public AuthService(LoginRepository loginRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public LoginResponse login(LoginRequest loginRequest) {

        SignupEntity user = loginRepository.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");

        }

        return new LoginResponse(jwtService.generateToken(user.getUsername()));

    }



}
