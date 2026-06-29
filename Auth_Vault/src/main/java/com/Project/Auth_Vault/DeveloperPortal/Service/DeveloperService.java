package com.Project.Auth_Vault.DeveloperPortal.Service;

import com.Project.Auth_Vault.DeveloperPortal.DTO.*;
import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import com.Project.Auth_Vault.DeveloperPortal.Repository.DeveloperRepository;
import com.Project.Auth_Vault.GlobalException.InValidCredentialException;
import com.Project.Auth_Vault.GlobalException.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeveloperJwtService developerJwtService;

    public DeveloperService(DeveloperRepository developerRepository,
                            PasswordEncoder passwordEncoder,
                            DeveloperJwtService developerJwtService) {
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
        this.developerJwtService = developerJwtService;
    }

    public DeveloperSignupResponse signup(DeveloperSignupRequest request) {
        if (developerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        DeveloperEntity developer = new DeveloperEntity();
        developer.setName(request.getName());
        developer.setEmail(request.getEmail());
        developer.setPassword(passwordEncoder.encode(request.getPassword()));
        developer.setApiKey(UUID.randomUUID().toString().replace("-", ""));
        developer.setCreatedAt(Instant.now());

        developerRepository.save(developer);

        return new DeveloperSignupResponse(developer.getEmail(), developer.getApiKey());
    }

    public DeveloperLoginResponse login(DeveloperLoginRequest request) {
        DeveloperEntity developer = developerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Developer not found"));

        if (!passwordEncoder.matches(request.getPassword(), developer.getPassword())) {
            throw new InValidCredentialException("Invalid credentials");
        }

        String accessToken = developerJwtService.generateToken(developer.getEmail());

        return new DeveloperLoginResponse(
                developer.getName(),    // ye missing tha
                developer.getEmail(),
                developer.getApiKey(),
                accessToken
        );
    }
}