package com.alexmita.ordermanagementsystem.domain.order;

import com.alexmita.ordermanagementsystem.domain.order.dtos.CreateOrderRequest;
import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderCreatedEvent;
import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderItemResponse;
import com.alexmita.ordermanagementsystem.domain.product.Product;
import com.alexmita.ordermanagementsystem.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderItemService(ProductRepository productRepository,
                            OrderItemRepository orderItemRepository,
                            ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public OrderItemResponse createOrder(CreateOrderRequest request) {
        List<OrderItem> orderItemsToSave = new ArrayList<>();

        for (var itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with id: " + itemRequest.productId() + " doesn't exist"));

            if (product.getStock() < itemRequest.quantity()) {
                throw new IllegalStateException("Out of stock " + product.getName() +
                        ". Only: " + product.getStock() + ", Asked: " + itemRequest.quantity());
            }

            product.setStock(product.getStock() - itemRequest.quantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(product, itemRequest.quantity());
            orderItemsToSave.add(orderItem);
        }

        List<OrderItem> savedItems = orderItemRepository.saveAll(orderItemsToSave);
        OrderItemResponse response = new OrderItemResponse(savedItems);

        eventPublisher.publishEvent(toOrderCreatedEvent(savedItems));

        return response;
    }

    private OrderCreatedEvent toOrderCreatedEvent(List<OrderItem> items) {
        var lines = items.stream()
                .map(i -> new OrderCreatedEvent.OrderItemLine(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity()))
                .toList();

        Long orderId = Long.valueOf(items.getFirst().getId());

        return new OrderCreatedEvent(orderId, lines, Instant.now());
    }
}