package com.ordersystem.scheduler;

import com.ordersystem.entity.Order;
import com.ordersystem.enums.OrderStatus;
import com.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {

    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void processPendingOrders() {

        List<Order> pendingOrders =
                orderRepository.findByStatus(
                        OrderStatus.PENDING);

        if (pendingOrders.isEmpty()) {
            return;
        }

        pendingOrders.forEach(order ->
                order.setStatus(
                        OrderStatus.PROCESSING));

        orderRepository.saveAll(
                pendingOrders);

        log.info("Updated {} orders",
                pendingOrders.size());
    }
}