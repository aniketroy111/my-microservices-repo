package com.dev.microservices.authservice.dto.external;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
