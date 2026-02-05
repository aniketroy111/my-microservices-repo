package com.dev.microservices.orderservice.service;


import org.springframework.stereotype.Service;
import com.dev.microservices.orderservice.dto.OrderRequest;
import com.dev.microservices.orderservice.dto.OrderResponse;
import com.dev.microservices.orderservice.dto.external.ProductResponseDTO;
import com.dev.microservices.orderservice.dto.external.UserResponseDTO;
import com.dev.microservices.orderservice.model.Order;
import com.dev.microservices.orderservice.exception.InsufficientStockException;
import com.dev.microservices.orderservice.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ExternalServiceCaller externalServiceCaller;

    public OrderService(OrderRepository orderRepository,ExternalServiceCaller externalServiceCaller) {
        this.orderRepository = orderRepository;
        this.externalServiceCaller = externalServiceCaller;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        //1) Call User Service
        UserResponseDTO user = externalServiceCaller.fetchUser(request.getUserId());

        //2) Call Product Service
        ProductResponseDTO product = externalServiceCaller.fetchProduct(request.getProductId());

        //3) Check stock
        if (product.getStock() < request.getQuantity()) {
            throw new InsufficientStockException(
                    "Not enough stock. Available=" + product.getStock() + ", Requested=" + request.getQuantity()
            );
        }

        //4) totalPrice
        double totalPrice = product.getPrice() * request.getQuantity();

        // 5) check inventory
        externalServiceCaller.reduceInventory(request.getProductId(),request.getQuantity());

        // 6) Save order
        Order saved = orderRepository.save(Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .build());

        //6) Response
        return OrderResponse.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .productId(saved.getProductId())
                .quantity(saved.getQuantity())
                .totalPrice(saved.getTotalPrice())
                .createdAt(saved.getCreatedAt())
                .userName(user.getName())
                .productName(product.getName())
                .build();
    }

}
