package com.dev.microservices.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class LoginRequest {

    @NotBlank(message = "Email must be required")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Password must be required")
    private String password;
}
