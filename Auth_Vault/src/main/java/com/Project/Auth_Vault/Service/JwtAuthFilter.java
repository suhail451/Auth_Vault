package com.Project.Auth_Vault.Service;

import com.Project.Auth_Vault.GlobalException.InValidTokenException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // step 1 — header padho
        String authHeader = request.getHeader("Authorization");

        // step 2 — Bearer check
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // aage jane do
            return;
        }

        // step 3 — token nikalo
        String token = authHeader.substring(7);

        // step 4 — username nikalo
        String username = null;
        try {
            username = jwtService.extractUsername(token);
        } catch (InValidTokenException e) {
            throw new InValidTokenException("Token is Tampered ");
        }

        // step 5 — SecurityContext checfk
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // step 6 — DB se user load karo
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // step 7 — validate karo
            if (jwtService.validateToken(token, username)) {


                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // step 9 — request aage bhejo
        filterChain.doFilter(request, response);
    }
}