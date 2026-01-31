package com.dev.microservices.authservice.dto;


import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class RegisterResponse {
    private Long userId;
    private String name;
    private String email;
}
