package com.alexmita.ordermanagementsystem.domain.product.dtos;

public record ProductDTO(
        Integer id,
        String name,
        Double price,
        Integer stock
) {}
