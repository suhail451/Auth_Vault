package com.Project.Auth_Vault.DeveloperPortal.Repository;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.OpenRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenRouteRepository extends JpaRepository<OpenRoute, Long> {

    List<OpenRoute> findAllByClientApp(ClientApp clientApp);

    boolean existsByClientAppAndHttpMethodAndPathAndRouteType(
            ClientApp clientApp,
            String httpMethod,
            String path,
            String routeType
    );
}