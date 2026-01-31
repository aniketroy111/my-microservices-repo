package com.dev.microservices.orderservice.controller;

import com.dev.microservices.orderservice.dto.OrderRequest;
import com.dev.microservices.orderservice.dto.OrderResponse;
import com.dev.microservices.orderservice.response.ApiResponse;
import com.dev.microservices.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.placeOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order placed successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
