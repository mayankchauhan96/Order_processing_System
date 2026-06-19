package com.ordersystem.controller;

import com.ordersystem.dto.*;
import com.ordersystem.enums.OrderStatus;
import com.ordersystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(
            @Valid
            @RequestBody
            CreateOrderRequest request) {

        return orderService
                .createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(
            @PathVariable UUID id) {

        return orderService
                .getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> getOrders(

            @RequestParam(
                    required = false)
            OrderStatus status) {

        return orderService
                .getAllOrders(status);
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(

            @PathVariable UUID id,

            @RequestBody
            @Valid
            UpdateStatusRequest request) {

        return orderService
                .updateOrderStatus(
                        id,
                        request);
    }

    @DeleteMapping("/{id}")
    public void cancelOrder(
            @PathVariable UUID id) {

        orderService.cancelOrder(id);
    }
}