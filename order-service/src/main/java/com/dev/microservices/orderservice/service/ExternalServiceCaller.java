package com.dev.microservices.orderservice.service;


import com.dev.microservices.orderservice.client.ProductServiceClient;
import com.dev.microservices.orderservice.client.UserServiceClient;
import com.dev.microservices.orderservice.dto.external.ApiResponseWrapper;
import com.dev.microservices.orderservice.dto.external.ProductResponseDTO;
import com.dev.microservices.orderservice.dto.external.UserResponseDTO;
import com.dev.microservices.orderservice.exception.ProductServiceUnavailableException;
import com.dev.microservices.orderservice.exception.UserServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceCaller {

    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    public ExternalServiceCaller(UserServiceClient userServiceClient,
                                 ProductServiceClient productServiceClient) {
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public UserResponseDTO fetchUser(Long userId) {
        ApiResponseWrapper<UserResponseDTO> userApi = userServiceClient.getUserById(userId);

        if (userApi == null || !userApi.isSuccess() || userApi.getData() == null) {
            throw new UserServiceUnavailableException("User not found or invalid response from user-service");
        }

        return userApi.getData();
    }

    public UserResponseDTO userFallback(Long userId, Throwable ex) {
        throw new UserServiceUnavailableException("User-Service is down. Please try again later.");
    }

    @Retry(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public ProductResponseDTO fetchProduct(Long productId) {
        ApiResponseWrapper<ProductResponseDTO> productApi = productServiceClient.getProductById(productId);

        if (productApi == null || !productApi.isSuccess() || productApi.getData() == null) {
            throw new ProductServiceUnavailableException("Product not found or invalid response from product-service");
        }

        return productApi.getData();
    }

    public ProductResponseDTO productFallback(Long productId, Throwable ex) {
        throw new ProductServiceUnavailableException("Product-Service is down. Please try again later.");
    }
}
