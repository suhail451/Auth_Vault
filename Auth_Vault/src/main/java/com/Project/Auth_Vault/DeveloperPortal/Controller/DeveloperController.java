package com.Project.Auth_Vault.DeveloperPortal.Controller;

import com.Project.Auth_Vault.DeveloperPortal.DTO.*;
import com.Project.Auth_Vault.DeveloperPortal.Service.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<DeveloperSignupResponse> signup(@RequestBody DeveloperSignupRequest request) {
        return new ResponseEntity<>(developerService.signup(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<DeveloperLoginResponse> login(@RequestBody DeveloperLoginRequest request) {
        return new ResponseEntity<>(developerService.login(request), HttpStatus.OK);
    }
}