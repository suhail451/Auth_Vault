package com.Project.Auth_Vault.Engine.DTO;

import lombok.Data;

@Data
public class ValidationRequest {
    private String token;
    private String clientId;
    private String path;
    private String method;
}