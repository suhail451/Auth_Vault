package com.Project.Auth_Vault.Engine.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
