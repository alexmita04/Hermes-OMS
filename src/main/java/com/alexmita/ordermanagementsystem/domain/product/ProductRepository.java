package com.alexmita.ordermanagementsystem.domain.product;

import com.alexmita.ordermanagementsystem.domain.product.dtos.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public ProductDTO getProductById(Integer productId);
}
