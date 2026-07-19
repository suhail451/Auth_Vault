package com.Project.Auth_Vault.DeveloperPortal.Controller;

import com.Project.Auth_Vault.DeveloperPortal.DTO.*;
import com.Project.Auth_Vault.DeveloperPortal.Service.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/developer")
@Tag(
        name = "Developer Authentication APIs",
        description = "APIs for developer registration and authentication."
)
public class DeveloperController {

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Operation(
            summary = "Register Developer",
            description = "Registers a new developer account using name, email, and password."
    )
    @PostMapping("/signup")
    public ResponseEntity<DeveloperSignupResponse> signup(@RequestBody DeveloperSignupRequest request) {
        return new ResponseEntity<>(developerService.signup(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Authenticate Developer",
            description = "Authenticates the developer and returns JWT tokens."
    )
    @PostMapping("/login")
    public ResponseEntity<DeveloperLoginResponse> login(@RequestBody DeveloperLoginRequest request) {
        return new ResponseEntity<>(developerService.login(request), HttpStatus.OK);
    }
}