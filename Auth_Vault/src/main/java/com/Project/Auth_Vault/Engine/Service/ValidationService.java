package com.Project.Auth_Vault.Engine.Service;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import com.Project.Auth_Vault.DeveloperPortal.Repository.ClientAppRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.DeveloperRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.OpenRouteRepository;
import com.Project.Auth_Vault.Engine.DTO.ValidationRequest;
import com.Project.Auth_Vault.Engine.DTO.ValidationResponse;
import com.Project.Auth_Vault.GlobalException.InValidTokenException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
public class ValidationService {

    private final DeveloperRepository developerRepository;
    private final ClientAppRepository clientAppRepository;
    private final OpenRouteRepository openRouteRepository;
    private final JwtService jwtService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidationService(DeveloperRepository developerRepository,
                             ClientAppRepository clientAppRepository,
                             OpenRouteRepository openRouteRepository,
                             JwtService jwtService) {
        this.developerRepository = developerRepository;
        this.clientAppRepository = clientAppRepository;
        this.openRouteRepository = openRouteRepository;
        this.jwtService = jwtService;
    }

    public ValidationResponse validateRequest(String apiKey, ValidationRequest req) {

        // 1. Validate Developer API Key
        DeveloperEntity developer = developerRepository.findByApiKey(apiKey).orElse(null);
        if (developer == null) {
            return null; // Invalid developer key
        }

        // 2. Validate Client App ownership
        ClientApp app = clientAppRepository
                .findByClientIdAndDeveloper(req.getClientId(), developer)
                .orElse(null);
        if (app == null) {
            return new ValidationResponse(false, null); // App does not match this developer
        }

        // 3. Is this route explicitly marked "protected" in the dashboard?
        boolean isProtectedDirect = openRouteRepository
                .existsByClientAppAndHttpMethodAndPathAndRouteType(
                        app, req.getMethod(), req.getPath(), "protected"
                );

        boolean isProtectedMatched = isProtectedDirect;
        if (!isProtectedDirect) {
            isProtectedMatched = openRouteRepository.findAllByClientApp(app).stream()
                    .filter(r -> r.getHttpMethod().equalsIgnoreCase(req.getMethod())
                            && "protected".equalsIgnoreCase(r.getRouteType()))
                    .anyMatch(r -> pathMatcher.match(r.getPath(), req.getPath()));
        }

        if (isProtectedMatched) {
            // Strict path: token MUST be a CLIENT token scoped to THIS app
            return validateClientJwt(req.getToken(), req.getClientId());
        }

        // 4. Anything not marked protected (explicitly "open" or simply unlisted)
        // is allowed through by default. We still require a structurally valid,
        // unexpired JWT so garbage/missing tokens don't sneak through, but we
        // don't enforce role or clientId scoping here.
        return validateAnyJwt(req.getToken());
    }

    /**
     * Used for PROTECTED routes only.
     * Rejects anything that isn't a CLIENT-role token scoped to the
     * requesting app's clientId — this is what stops a developer token
     * (token d) or another app's client token from getting in.
     */
    private ValidationResponse validateClientJwt(String token, String requestClientId) {
        if (token == null || token.isBlank()) {
            return new ValidationResponse(false, null);
        }
        try {
            if (jwtService.isTokenExpired(token)) {
                return new ValidationResponse(false, null);
            }

            String role = jwtService.extractRole(token);
            if (!"CLIENT".equals(role)) {
                // Developer token, or any non-client token — reject
                return new ValidationResponse(false, null);
            }

            String tokenClientId = jwtService.extractClientId(token);
            if (tokenClientId == null || !tokenClientId.equals(requestClientId)) {
                // Valid client token, but issued for a different app
                return new ValidationResponse(false, null);
            }

            String username = jwtService.extractUsername(token);
            return new ValidationResponse(true, username);

        } catch (InValidTokenException e) {
            return new ValidationResponse(false, null);
        }
    }

    /**
     * Used for open/unlisted routes.
     * Just needs a structurally valid, unexpired, correctly signed JWT —
     * no role or clientId enforcement.
     */
    private ValidationResponse validateAnyJwt(String token) {
        if (token == null || token.isBlank()) {
            return new ValidationResponse(false, null);
        }
        try {
            if (jwtService.isTokenExpired(token)) {
                return new ValidationResponse(false, null);
            }
            String username = jwtService.extractUsername(token);
            return new ValidationResponse(true, username);
        } catch (InValidTokenException e) {
            return new ValidationResponse(false, null);
        }
    }
}