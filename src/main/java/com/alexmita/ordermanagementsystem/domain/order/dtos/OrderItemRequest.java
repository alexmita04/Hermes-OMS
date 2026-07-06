package com.alexmita.ordermanagementsystem.domain.order.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull(message = "id is mandatory")
        Integer productId,

        @NotNull(message = "quantity is mandatory")
        @Min(value = 1, message = "quantity must be at leat 800")
        Integer quantity
) {}