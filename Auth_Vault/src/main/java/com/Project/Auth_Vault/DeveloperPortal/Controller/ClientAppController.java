package com.Project.Auth_Vault.DeveloperPortal.Controller;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.OpenRoute;
import com.Project.Auth_Vault.DeveloperPortal.Service.ClientAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/apps")
@RequiredArgsConstructor
public class ClientAppController {

    private final ClientAppService clientAppService;

    // App banao
    @PostMapping("/create")
    public ResponseEntity<?> createApp(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody Map<String, String> body
    ) {
        ClientApp app = clientAppService.createApp(apiKey, body.get("appName"));
        return ResponseEntity.ok(Map.of(
                "clientId", app.getClientId(),
                "appName",  app.getAppName(),
                "createdAt", app.getCreatedAt()
        ));
    }

    // Saari apps dekho
    @GetMapping

    public ResponseEntity<?> getApps(@RequestHeader("X-API-KEY") String apiKey) {
        return ResponseEntity.ok(clientAppService.getApps(apiKey));
    }

    // Route add karo
    @PostMapping("/{clientId}/routes")
    public ResponseEntity<?> addRoute(
            @RequestHeader("X-API-KEY") String apiKey,
            @PathVariable String clientId,
            @RequestBody Map<String, String> body
    ) {
        OpenRoute r = clientAppService.addRoute(
                apiKey, clientId,
                body.get("method"),
                body.get("path"),
                body.get("routeType")
        );
        return ResponseEntity.ok(Map.of(
                "id",        r.getId(),
                "method",    r.getHttpMethod(),
                "path",      r.getPath(),
                "routeType", r.getRouteType()
        ));
    }

    // Route delete karo
    @DeleteMapping("/{clientId}/routes/{routeId}")
    public ResponseEntity<?> deleteRoute(
            @RequestHeader("X-API-KEY") String apiKey,
            @PathVariable String clientId,
            @PathVariable Long routeId
    ) {
        clientAppService.deleteRoute(apiKey, clientId, routeId);
        return ResponseEntity.ok(Map.of("message", "Route deleted"));
    }
}