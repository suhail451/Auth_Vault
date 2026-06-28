package com.Project.Auth_Vault.Engine.Repository;


import com.Project.Auth_Vault.Engine.Entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

        Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByToken(String token);

}

