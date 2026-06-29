package com.Project.Auth_Vault.DeveloperPortal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeveloperSignupResponse {
    private String email;
    private String apiKey;
}