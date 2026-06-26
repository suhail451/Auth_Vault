package com.Project.Auth_Vault.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Registration request body")

public class SignupRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "User's email address", example = "john@example.com")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 8 characters")
    @Schema(description = "Password (min 5 chars)", example = "Pass@1234")
    private String password;

}
