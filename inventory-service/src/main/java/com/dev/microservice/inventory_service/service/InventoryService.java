package com.dev.microservice.inventory_service.service;

import com.dev.microservice.inventory_service.dto.InventoryRequest;
import com.dev.microservice.inventory_service.dto.InventoryResponse;
import com.dev.microservice.inventory_service.exception.InsufficientStockException;
import com.dev.microservice.inventory_service.exception.ProductNotFoundInInventoryException;
import com.dev.microservice.inventory_service.model.Inventory;
import com.dev.microservice.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryResponse getStock(Long productId){
        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow(()->new ProductNotFoundInInventoryException("Product not found in inventory"));
        return mapToResponse(inventory);
    }

    public void reduceStock(InventoryRequest request) {

        Inventory inventory = inventoryRepository
                .findByProductId(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundInInventoryException("Product not found in inventory"));

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        inventoryRepository.save(inventory);
    }

    public InventoryResponse mapToResponse(Inventory inventory){
        return InventoryResponse.builder()
                .productId(inventory.getProductId())
                .quantity(inventory.getQuantity()).build();
    }
}
