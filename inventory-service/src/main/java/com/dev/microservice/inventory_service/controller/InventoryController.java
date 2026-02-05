package com.dev.microservice.inventory_service.controller;

import com.dev.microservice.inventory_service.dto.InventoryRequest;
import com.dev.microservice.inventory_service.dto.InventoryResponse;
import com.dev.microservice.inventory_service.response.ApiResponse;
import com.dev.microservice.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getStock(@PathVariable Long productId){
        InventoryResponse inventoryResponse = inventoryService.getStock(productId);
        return ResponseEntity.ok(ApiResponse.<InventoryResponse>builder()
                .success(true)
                .message("stock fetch successfully")
                .data(inventoryResponse)
                .timestamp(LocalDateTime.now())
                .build());
    }
    @PostMapping("/reduce")
    public ResponseEntity<ApiResponse<String>> reduceStock(@RequestBody InventoryRequest request) {
        inventoryService.reduceStock(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Stock reduced successfully")
                .data(null)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
