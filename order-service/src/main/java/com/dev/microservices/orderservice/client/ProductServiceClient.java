package com.dev.microservices.orderservice.client;

import com.dev.microservices.orderservice.dto.external.ApiResponseWrapper;
import com.dev.microservices.orderservice.dto.external.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    ApiResponseWrapper<ProductResponseDTO> getProductById(@PathVariable("id") Long id);
}