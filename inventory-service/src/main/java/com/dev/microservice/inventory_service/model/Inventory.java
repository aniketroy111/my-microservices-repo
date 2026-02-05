package com.dev.microservice.inventory_service.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "inventory")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
}
