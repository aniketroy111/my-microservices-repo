package com.dev.microservices.orderservice.client;

import com.dev.microservices.orderservice.dto.external.ApiResponseWrapper;
import com.dev.microservices.orderservice.dto.external.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    ApiResponseWrapper<UserResponseDTO> getUserById(@PathVariable("id") Long id);
}