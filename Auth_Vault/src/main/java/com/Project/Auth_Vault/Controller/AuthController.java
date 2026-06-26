package com.Project.Auth_Vault.Controller;


import com.Project.Auth_Vault.DTO.LoginRequest;
import com.Project.Auth_Vault.DTO.LoginResponse;
import com.Project.Auth_Vault.DTO.RefreshRequest;
import com.Project.Auth_Vault.Service.AuthService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        try {
            return ResponseEntity.ok(authService.refresh(request));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshRequest request) {
        try {
            authService.logout(request);
            return ResponseEntity.ok("Logged out successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }




}
