package com.Project.Auth_Vault.Engine.Service;

import com.Project.Auth_Vault.DeveloperPortal.Entity.ClientApp;
import com.Project.Auth_Vault.DeveloperPortal.Entity.DeveloperEntity;
import com.Project.Auth_Vault.DeveloperPortal.Repository.ClientAppRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.DeveloperRepository;
import com.Project.Auth_Vault.DeveloperPortal.Repository.OpenRouteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final DeveloperRepository developerRepository;
    private final ClientAppRepository clientAppRepository;
    private final OpenRouteRepository openRouteRepository;

    public ApiKeyFilter(DeveloperRepository developerRepository,
                        ClientAppRepository clientAppRepository,
                        OpenRouteRepository openRouteRepository) {
        this.developerRepository = developerRepository;
        this.clientAppRepository = clientAppRepository;
        this.openRouteRepository = openRouteRepository;
    }

    // Sirf ye routes skip honge — developer login/signup aur swagger
    private static final List<String> SKIP_PATHS = List.of(
            "/developer/signup",
            "/developer/login",
            "/swagger-ui",
            "/v3/api-docs",
            "/api/authvault/validate"
    );
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return SKIP_PATHS.stream().anyMatch(path::startsWith) || path.equals("/api/authvault/validate");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // OPTIONS preflight skip karo
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // SKIP_PATHS check
        boolean shouldSkip = SKIP_PATHS.stream().anyMatch(path::startsWith);
        if (shouldSkip) {
            filterChain.doFilter(request, response);
            return;
        }

        // Har doosri request pe API key lazim hai
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null || apiKey.isBlank()) {
            sendError(response, 401, "X-API-KEY header missing");
            return;
        }

        DeveloperEntity developer = developerRepository.findByApiKey(apiKey).orElse(null);
        if (developer == null) {
            sendError(response, 401, "Invalid API Key");
            return;
        }

        // /api/apps/** routes ke liye bas API key kaafi hai — aage jane do
        if (path.startsWith("/api/apps")) {
            filterChain.doFilter(request, response);
            return;
        }

        // /auth/** routes ke liye CLIENT ID bhi check karo
        String clientId = request.getHeader("X-CLIENT-ID");
        if (clientId != null && !clientId.isBlank()) {
            ClientApp app = clientAppRepository
                    .findByClientIdAndDeveloper(clientId, developer)
                    .orElse(null);

            if (app == null) {
                sendError(response, 403, "Invalid Client ID or unauthorized");
                return;
            }

            boolean isOpen = openRouteRepository
                    .existsByClientAppAndHttpMethodAndPathAndRouteType(
                            app, method, path, "open"
                    );

            if (isOpen) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response,
                           int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"status\":%d,\"message\":\"%s\"}", status, message)
        );
    }
}