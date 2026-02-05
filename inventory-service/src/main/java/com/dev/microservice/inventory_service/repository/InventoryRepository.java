package com.dev.microservice.inventory_service.repository;

import com.dev.microservice.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findByProductId(Long aLong);
}
