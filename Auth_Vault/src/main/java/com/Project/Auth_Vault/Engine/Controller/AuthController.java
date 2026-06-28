package com.Project.Auth_Vault.Engine.Controller;


import com.Project.Auth_Vault.Engine.DTO.LoginRequest;
import com.Project.Auth_Vault.Engine.DTO.LoginResponse;
import com.Project.Auth_Vault.Engine.DTO.RefreshRequest;
import com.Project.Auth_Vault.Engine.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


   @PostMapping("/login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest loginRequest){

        LoginResponse response =authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody RefreshRequest request) {
        String accessToken = authHeader.substring(7);
        authService.logout(accessToken, request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }




}
