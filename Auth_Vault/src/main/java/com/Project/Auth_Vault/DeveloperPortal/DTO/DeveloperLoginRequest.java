package com.Project.Auth_Vault.DeveloperPortal.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperLoginRequest {
    private String email;
    private String password;
}