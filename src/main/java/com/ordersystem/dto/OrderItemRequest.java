package com.ordersystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotBlank
    private String productName;

    @Min(1)
    private Integer quantity;

    @Min(1)
    private Double price;
}