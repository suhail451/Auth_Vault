package com.Project.Auth_Vault.Engine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class    LoginRequest {

    private String username;
    private String password;
    private String clientId;


}
