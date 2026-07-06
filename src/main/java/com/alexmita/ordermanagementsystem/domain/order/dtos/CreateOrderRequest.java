package com.alexmita.ordermanagementsystem.domain.order.dtos;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderRequest(
        @NotEmpty(message = "Order should have at least one product")
        List<OrderItemRequest> items
) {}