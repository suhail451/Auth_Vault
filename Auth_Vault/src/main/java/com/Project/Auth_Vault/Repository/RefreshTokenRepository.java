package com.Project.Auth_Vault.Repository;


import com.Project.Auth_Vault.Entity.RefreshTokenEntity;
import com.Project.Auth_Vault.Entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

        Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByToken(String token);

}

