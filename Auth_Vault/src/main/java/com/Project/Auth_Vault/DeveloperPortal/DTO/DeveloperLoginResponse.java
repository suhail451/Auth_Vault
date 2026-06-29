package com.Project.Auth_Vault.DeveloperPortal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeveloperLoginResponse {
    private String name;
    private String email;
    private String apiKey;
    private String accessToken;
}