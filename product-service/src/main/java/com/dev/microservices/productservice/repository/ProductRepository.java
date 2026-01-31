package com.dev.microservices.productservice.repository;

import com.dev.microservices.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
