package com.Project.Auth_Vault.Engine.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> myBucket = new ConcurrentHashMap<>();

    private Bucket getBucket(String ip) {
        return myBucket.computeIfAbsent(ip, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.greedy(50, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(50, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private static final java.util.List<String> SKIP_PATHS = java.util.List.of(
            "/developer/",
            "/swagger-ui",
            "/v3/api-docs",
            "/api/apps"       // skip karo — developer dashboard calls
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // OPTIONS preflight skip
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Skip paths
        boolean shouldSkip = SKIP_PATHS.stream().anyMatch(path::startsWith);
        if (shouldSkip) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();
        Bucket bucket = getBucket(ip);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"status\":429,\"message\":\"Too many requests — try again in 1 minute\"}"
            );
        }
    }
}