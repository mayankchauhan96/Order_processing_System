package com.ordersystem.dto;

import com.ordersystem.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {

    @NotNull
    private OrderStatus status;
}