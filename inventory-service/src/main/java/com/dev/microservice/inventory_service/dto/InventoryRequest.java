package com.dev.microservice.inventory_service.dto;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class InventoryRequest {
    private Long productId;
    private Integer quantity;
}
