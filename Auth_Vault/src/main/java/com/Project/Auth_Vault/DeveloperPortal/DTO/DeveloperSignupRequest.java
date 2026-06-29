package com.Project.Auth_Vault.DeveloperPortal.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperSignupRequest {
    private String name;
    private String email;
    private String password;
}