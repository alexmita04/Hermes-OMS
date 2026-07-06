package com.alexmita.ordermanagementsystem.domain.product;

import com.alexmita.ordermanagementsystem.domain.product.dtos.AllProductsResponse;
import com.alexmita.ordermanagementsystem.domain.product.dtos.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory/products")
public class ProductController {
    private final ProductService prodcutService;

    public ProductController(ProductService prodcutService) {
        this.prodcutService = prodcutService;
    }

    @GetMapping()
    public ResponseEntity<AllProductsResponse> getAllProducts() {
        AllProductsResponse response = prodcutService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer productId) {
        ProductDTO product = prodcutService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
