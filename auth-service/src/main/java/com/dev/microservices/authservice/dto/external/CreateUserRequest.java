package com.dev.microservices.authservice.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserRequest {
    private String name;
    private String email;
}