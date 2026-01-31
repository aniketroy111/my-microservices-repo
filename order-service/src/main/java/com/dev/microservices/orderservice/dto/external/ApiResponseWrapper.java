package com.dev.microservices.orderservice.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private String timestamp;
}