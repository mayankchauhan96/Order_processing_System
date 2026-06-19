package com.ordersystem.service;

import com.ordersystem.dto.*;
import com.ordersystem.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(
            CreateOrderRequest request
    );

    OrderResponse getOrderById(
            UUID orderId
    );

    List<OrderResponse> getAllOrders(
            OrderStatus status
    );

    OrderResponse updateOrderStatus(
            UUID orderId,
            UpdateStatusRequest request
    );

    void cancelOrder(
            UUID orderId
    );
}