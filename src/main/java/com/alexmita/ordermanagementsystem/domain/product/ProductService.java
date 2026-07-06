package com.alexmita.ordermanagementsystem.domain.product;

import com.alexmita.ordermanagementsystem.domain.product.dtos.AllProductsResponse;
import com.alexmita.ordermanagementsystem.domain.product.dtos.ProductDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_KEY_PREFIX = "product:";

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    public AllProductsResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();

        AllProductsResponse response = new AllProductsResponse("good",
                productList.toArray().length,
                productList);

        return response;
    }

    public ProductDTO getProductById(Integer productId) {
        String redisKey = CACHE_KEY_PREFIX + productId;
        ProductDTO cachedProduct = (ProductDTO) redisTemplate.opsForValue().get(redisKey);

        // cache hit
        if(cachedProduct != null) {
            System.out.println("cache hit");
            return cachedProduct;
        }

        // cache miss
        ProductDTO product = productRepository.getProductById(productId);

        if(product != null) {
            redisTemplate.opsForValue().set(redisKey, product, Duration.ofMinutes(10));
        }

        return product;
    }
}
