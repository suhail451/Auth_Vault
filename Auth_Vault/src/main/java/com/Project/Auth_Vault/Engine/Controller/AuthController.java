package com.Project.Auth_Vault.Engine.Controller;


import com.Project.Auth_Vault.Engine.DTO.LoginRequest;
import com.Project.Auth_Vault.Engine.DTO.LoginResponse;
import com.Project.Auth_Vault.Engine.DTO.RefreshRequest;
import com.Project.Auth_Vault.Engine.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication APIs",
        description = "APIs for login, refresh token generation and logout."
)
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(
            summary = "Authenticate user",
            description = "Authenticates the user using email and password and returns an access token and refresh token."
    )

     @PostMapping("/login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest loginRequest){

        LoginResponse response =authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Generate new access token",
            description = "Generates a new JWT access token using a valid refresh token."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }


    @Operation(
            summary = "Logout user",
            description = "Invalidates the provided access token and refresh token."
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody RefreshRequest request) {
        String accessToken = authHeader.substring(7);
        authService.logout(accessToken, request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }




}
