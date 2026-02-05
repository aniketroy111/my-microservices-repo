package com.dev.microservice.inventory_service.exception;

public class ProductNotFoundInInventoryException extends RuntimeException {
    public ProductNotFoundInInventoryException(String message) {
        super(message);
    }
}
