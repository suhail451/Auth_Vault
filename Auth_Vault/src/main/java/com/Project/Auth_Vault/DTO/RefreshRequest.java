package com.Project.Auth_Vault.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RefreshRequest {
    private String refreshToken;

}
