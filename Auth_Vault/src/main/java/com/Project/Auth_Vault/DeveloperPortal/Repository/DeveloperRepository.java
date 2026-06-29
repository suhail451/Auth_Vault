package com.Project.Auth_Vault.DeveloperPortal.Repository;

import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {
    Optional<DeveloperEntity> findByEmail(String email);
    Optional<DeveloperEntity> findByApiKey(String apiKey);

    boolean existsByEmail(String email);
    boolean existsByApiKey(String apiKey);
}