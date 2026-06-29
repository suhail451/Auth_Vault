package com.Project.Auth_Vault.DeveloperPortal.Service;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientAppDTO;
import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import com.Project.Auth_Vault.DeveloperPortal.Entity.OpenRoute;
import com.Project.Auth_Vault.DeveloperPortal.Repository.ClientAppRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.DeveloperRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.OpenRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientAppService {

    private final ClientAppRepository clientAppRepository;
    private final DeveloperRepository developerRepository;
    private final OpenRouteRepository openRouteRepository;

    // New app banao
    public ClientApp createApp(String apiKey, String appName) {
        DeveloperEntity dev = developerRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        ClientApp app = new ClientApp();
        app.setAppName(appName);
        app.setClientId("app_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        app.setDeveloper(dev);
        app.setCreatedAt(Instant.now());

        return clientAppRepository.save(app);
    }

    // Developer ki saari apps
    public List<ClientAppDTO> getApps(String apiKey) {
        DeveloperEntity dev = developerRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        return clientAppRepository.findAllByDeveloper(dev)
                .stream()
                .map(app -> new ClientAppDTO(
                        app.getClientId(),
                        app.getAppName(),
                        app.getRoutes() == null ? List.of() :
                                app.getRoutes().stream()
                                        .map(r -> new ClientAppDTO.RouteDTO(
                                                r.getId(),
                                                r.getHttpMethod(),
                                                r.getPath(),
                                                r.getRouteType()
                                        ))
                                        .toList()
                ))
                .toList();
    }
    // Route add karo
    public OpenRoute addRoute(String apiKey, String clientId,
                              String method, String path, String routeType) {

        DeveloperEntity dev = developerRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        // Verify — ye app is developer ki hai?
        ClientApp app = clientAppRepository
                .findByClientIdAndDeveloper(clientId, dev)
                .orElseThrow(() -> new RuntimeException("App not found or unauthorized"));

        OpenRoute route = new OpenRoute();
        route.setClientApp(app);
        route.setHttpMethod(method.toUpperCase());
        route.setPath(path);
        route.setRouteType(routeType);

        return openRouteRepository.save(route);
    }

    // Route delete karo
    public void deleteRoute(String apiKey, String clientId, Long routeId) {
        DeveloperEntity dev = developerRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        ClientApp app = clientAppRepository
                .findByClientIdAndDeveloper(clientId, dev)
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        openRouteRepository.deleteById(routeId);
    }
}