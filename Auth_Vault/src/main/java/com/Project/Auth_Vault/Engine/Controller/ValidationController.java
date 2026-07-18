package com.Project.Auth_Vault.Engine.Controller;

import com.Project.Auth_Vault.Engine.DTO.ValidationRequest;
import com.Project.Auth_Vault.Engine.DTO.ValidationResponse;
import com.Project.Auth_Vault.Engine.Service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authvault")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody ValidationRequest request) {

        // Use our validation service to verify developer API key, routes, and JWT
        ValidationResponse response = validationService.validateRequest(apiKey, request);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ValidationResponse(false, null, null));
        }

        return ResponseEntity.ok(response);
    }
}