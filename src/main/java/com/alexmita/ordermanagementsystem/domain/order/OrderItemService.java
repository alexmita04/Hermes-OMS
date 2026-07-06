package com.alexmita.ordermanagementsystem.domain.order;

import com.alexmita.ordermanagementsystem.domain.order.dtos.CreateOrderRequest;
import com.alexmita.ordermanagementsystem.domain.order.dtos.OrderItemResponse;
import com.alexmita.ordermanagementsystem.domain.product.Product;
import com.alexmita.ordermanagementsystem.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
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
        return new OrderItemResponse(savedItems);
    }
}