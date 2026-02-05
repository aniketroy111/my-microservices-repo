package com.dev.microservices.orderservice.client;

import com.dev.microservices.orderservice.dto.external.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @PostMapping("/inventory/reduce")
    public void reduceStock(@RequestBody InventoryRequest request);
}
