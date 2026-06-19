package com.ordersystem.dto;

import com.ordersystem.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {

    private UUID id;

    private String customerName;

    private Double totalAmount;

    private OrderStatus status;

    private List<OrderItemResponse> items;
}