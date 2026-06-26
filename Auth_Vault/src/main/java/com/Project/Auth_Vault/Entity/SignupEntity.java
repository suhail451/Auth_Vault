package com.Project.Auth_Vault.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor

public class SignupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    public SignupEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
