package com.alexmita.ordermanagementsystem.domain.product.dtos;

import com.alexmita.ordermanagementsystem.domain.product.Product;

import java.util.List;

public record AllProductsResponse(
        String status,
        Integer counter,
        List<Product> products
) {
}
