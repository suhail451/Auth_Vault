package com.Project.Auth_Vault.Engine.Repository;

import com.Project.Auth_Vault.Engine.Entity.BlockListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface BlockListTokenRepository extends JpaRepository<BlockListedToken,Long> {

    boolean existsByBlocklistedToken(String token);
      void deleteByExpiryDateBefore(Instant now);


}
