package com.Project.Auth_Vault.Controller;

import com.Project.Auth_Vault.DTO.SignupRequest;
import com.Project.Auth_Vault.DTO.SignupResponse;
import com.Project.Auth_Vault.Service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Register, Login, Refresh Token endpoints")
@RestController
@RequestMapping("/auth")
public class SignupController {


    final SignupService signupService;

     public SignupController(SignupService signupService){
        this.signupService=signupService;
    }
    @Operation(
            summary = "Register new user",
            description = "Creates a new user account and returns user details"
    )

    @PostMapping("/register")

    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest){


         SignupResponse response=signupService.saveUser(signupRequest);

            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }



}
