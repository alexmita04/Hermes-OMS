package com.alexmita.ordermanagementsystem.domain.order.dtos;

import java.time.Instant;
import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        List<OrderItemLine> items,
        Instant createdAt
) {
    public record OrderItemLine(Integer productId, String productName, int quantity) {}
}