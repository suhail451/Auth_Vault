package com.Project.Auth_Vault.DeveloperPortal.Controller;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.OpenRoute;
import com.Project.Auth_Vault.DeveloperPortal.Service.ClientAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/apps")
@RequiredArgsConstructor

@Tag(
        name = "Application Management APIs",
        description = "Endpoints for creating applications and managing protected routes."
)
public class ClientAppController {

    private final ClientAppService clientAppService;

    // App banao
    @Operation(
            summary = "Create Client Application",
            description = "Creates a new client application and returns a generated client ID."
    )
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


    @Operation(
            summary = "Get Registered Applications",
            description = "Returns all applications associated with the developer API key."
    )
    @GetMapping
    public ResponseEntity<?> getApps(@RequestHeader("X-API-KEY") String apiKey) {
        return ResponseEntity.ok(clientAppService.getApps(apiKey));
    }

    // Route add karo

    @Operation(
            summary = "Register Application Route",
            description = "Adds a route to the client application and marks it as protected or public."
    )
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

    @Operation(
            summary = "Delete Application Route",
            description = "Removes a registered route from the client application."
    )
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