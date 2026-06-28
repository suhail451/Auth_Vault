package com.Project.Auth_Vault.Engine.DTO;

import com.Project.Auth_Vault.Engine.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeResponse {

    private String username;
    private Set<Role> roles;



}