package com.dev.microservices.orderservice.dto.external;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class InventoryRequest {
    private Long productId;
    private Integer quantity;
}
