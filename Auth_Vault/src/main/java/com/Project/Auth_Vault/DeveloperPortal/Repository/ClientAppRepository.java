package com.Project.Auth_Vault.DeveloperPortal.Repository;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientAppRepository extends JpaRepository<ClientApp, Long> {

    Optional<ClientApp> findByClientId(String clientId);

    Optional<ClientApp> findByClientIdAndDeveloper(String clientId, DeveloperEntity developer);

    List<ClientApp> findAllByDeveloper(DeveloperEntity developer);
}