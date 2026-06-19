package com.mayank.ordersystem;

import com.ordersystem.entity.Order;
import com.ordersystem.enums.OrderStatus;
import com.ordersystem.exception.InvalidOrderStateException;
import com.ordersystem.repository.OrderRepository;
import com.ordersystem.service.OrderServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository repository;

    @InjectMocks
    OrderServiceImpl service;

    @Test
    void shouldCancelPendingOrder() {

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatus.PENDING);

        when(repository.findById(any()))
                .thenReturn(
                        Optional.of(order));

        service.cancelOrder(order.getId());

        verify(repository,
                times(1))
                .save(order);
    }

    @Test
    void shouldThrowForProcessedOrder() {

        Order order = new Order();
        order.setStatus(
                OrderStatus.SHIPPED);

        when(repository.findById(any()))
                .thenReturn(
                        Optional.of(order));

        assertThrows(
                InvalidOrderStateException.class,

                () -> service.cancelOrder(
                        UUID.randomUUID()
                ));
    }
}