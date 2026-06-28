package com.Project.Auth_Vault.Engine.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class BlockListedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String blocklistedToken;

    private Instant expiryDate;

}
