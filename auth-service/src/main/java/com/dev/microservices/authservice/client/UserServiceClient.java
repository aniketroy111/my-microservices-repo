package com.dev.microservices.authservice.client;

import com.dev.microservices.authservice.dto.external.CreateUserRequest;
import com.dev.microservices.authservice.dto.external.UserResponseDTO;
import com.dev.microservices.authservice.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/users")
    ApiResponse<UserResponseDTO> createUser(CreateUserRequest request);
}