package com.alexmita.ordermanagementsystem.domain.order.dtos;

import com.alexmita.ordermanagementsystem.domain.order.OrderItem;

import java.util.List;

public record OrderItemResponse(List<OrderItem> products) {}
