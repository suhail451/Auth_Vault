package com.Project.Auth_Vault.Engine.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResponse {
    private boolean valid;
    private String username;
    private String role;
}