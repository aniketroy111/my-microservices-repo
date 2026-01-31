package com.dev.microservices.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotNull(message = "Price must not be null")
    @Min(value = 1, message = "Price must be at least 1")
    private Double price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be 0 or more")
    private Integer stock;
}
