package com.ordersystem.service;

import com.ordersystem.dto.*;
import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderItem;
import com.ordersystem.enums.OrderStatus;
import com.ordersystem.exception.InvalidOrderStateException;
import com.ordersystem.exception.OrderNotFoundException;
import com.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(
            CreateOrderRequest request) {

        log.info("Creating order for customer {}",
                request.getCustomerName());

        List<OrderItem> items = new ArrayList<>();

        double total = request.getItems()
                .stream()
                .mapToDouble(
                        i -> i.getPrice()
                                * i.getQuantity()
                )
                .sum();

        Order order = Order.builder()
                .customerName(
                        request.getCustomerName())
                .status(OrderStatus.PENDING)
                .totalAmount(total)
                .build();

        for (OrderItemRequest dto :
                request.getItems()) {

            OrderItem item =
                    OrderItem.builder()
                            .productName(
                                    dto.getProductName())
                            .quantity(
                                    dto.getQuantity())
                            .price(dto.getPrice())
                            .subtotal(
                                    dto.getPrice()
                                            * dto.getQuantity())
                            .order(order)
                            .build();

            items.add(item);
        }

        order.setItems(items);

        Order saved =
                orderRepository.save(order);

        return mapToResponse(saved);
    }

    @Override
    public OrderResponse getOrderById(
            UUID orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found"));

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders(
            OrderStatus status) {

        List<Order> orders;

        if (status == null) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository
                    .findByStatus(status);
        }

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(
            UUID orderId,
            UpdateStatusRequest request) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found"));

        validateTransition(
                order.getStatus(),
                request.getStatus());

        order.setStatus(
                request.getStatus());

        Order updated =
                orderRepository.save(order);

        return mapToResponse(updated);
    }

    @Override
    public void cancelOrder(
            UUID orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found"));

        if (order.getStatus()
                != OrderStatus.PENDING) {

            throw new InvalidOrderStateException(
                    "Only pending orders can be cancelled"
            );
        }

        order.setStatus(
                OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    private void validateTransition(
            OrderStatus current,
            OrderStatus next) {

        boolean valid =
                (current == OrderStatus.PENDING
                        && next == OrderStatus.PROCESSING)

                ||

                (current == OrderStatus.PROCESSING
                        && next == OrderStatus.SHIPPED)

                ||

                (current == OrderStatus.SHIPPED
                        && next == OrderStatus.DELIVERED);

        if (!valid) {
            throw new InvalidOrderStateException(
                    "Invalid status transition"
            );
        }
    }

    private OrderResponse mapToResponse(
            Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(item ->
                                OrderItemResponse
                                        .builder()
                                        .productName(
                                                item.getProductName())
                                        .quantity(
                                                item.getQuantity())
                                        .price(
                                                item.getPrice())
                                        .subtotal(
                                                item.getSubtotal())
                                        .build())
                        .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .customerName(
                        order.getCustomerName())
                .totalAmount(
                        order.getTotalAmount())
                .status(order.getStatus())
                .items(items)
                .build();
    }
}