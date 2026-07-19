package com.Project.Auth_Vault.Engine.Controller;


import com.Project.Auth_Vault.Engine.DTO.MeResponse;
import com.Project.Auth_Vault.Engine.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Protected APIs",
        description = "Demonstration endpoints secured using JWT authentication."
)
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;

    }


    @Operation(
            summary = "Get current user details",
            description = "Returns information about the currently authenticated user based on the JWT token."
    )
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

    public ResponseEntity<MeResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        MeResponse response = authService.getMe(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}