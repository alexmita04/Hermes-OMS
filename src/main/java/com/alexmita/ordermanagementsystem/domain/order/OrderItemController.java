package com.alexmita.ordermanagementsystem.domain.order;

import com.alexmita.ordermanagementsystem.domain.order.dtos.CreateOrderRequest;
import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderItemController {

    private final OrderItemService orderService;

    public OrderItemController(OrderItemService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderItemResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
