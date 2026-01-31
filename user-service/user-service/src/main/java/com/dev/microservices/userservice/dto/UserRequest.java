package com.dev.microservices.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotBlank(message = "Email must be not blank")
    @Email(message = "Email must be valid")
    private String email;

}
