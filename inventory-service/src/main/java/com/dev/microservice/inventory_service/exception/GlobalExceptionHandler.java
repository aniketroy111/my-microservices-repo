package com.dev.microservice.inventory_service.exception;

import com.dev.microservice.inventory_service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundInInventoryException.class)
    public ResponseEntity<ApiResponse<Object>> handleProductNotFoundInInventory(ProductNotFoundInInventoryException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(build(ex.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<Object>> handleInsufficientStock(ProductNotFoundInInventoryException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(build(ex.getMessage()));
    }

    private ApiResponse<Object> build(String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
