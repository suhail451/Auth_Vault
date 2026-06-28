package com.Project.Auth_Vault.Engine.Controller;


import com.Project.Auth_Vault.Engine.DTO.MeResponse;
import com.Project.Auth_Vault.Engine.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthService authService;
//    private final MeResponse response;

    public UserController(AuthService authService) {
        this.authService = authService;
    //        this.response = response;
    }

    // @AuthenticationPrincipal — SecurityContext se logged in user automatically inject hota hai
    // manually token parse karne ki zaroorat nahi
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MeResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {

        // userDetails.getUsername() — SecurityContext se username nikal ke service ko dete hain
        MeResponse response = authService.getMe(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}