package com.alexmita.ordermanagementsystem.domain.product;

import com.alexmita.ordermanagementsystem.domain.product.dtos.AllProductsResponse;
import com.alexmita.ordermanagementsystem.domain.product.dtos.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public AllProductsResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();

        AllProductsResponse response = new AllProductsResponse("good",
                productList.toArray().length,
                productList);

        return response;
    }

    public ProductDTO getProductById(Integer productId) {
        ProductDTO product = productRepository.getProductById(productId);

        return product;
    }
}
