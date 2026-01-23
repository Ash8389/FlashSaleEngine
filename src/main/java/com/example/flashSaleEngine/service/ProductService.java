package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.model.Product;
import com.example.flashSaleEngine.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String STOCK_PREFIX = "product_stock:";

    @PostConstruct
    public void init() {

        String productId = "IPHONE_15_PRO";

        Product product = productRepository.findById(productId)
                .orElseGet(() -> {
                    Product p = new Product();
                    p.setId(productId);
                    p.setName("iPhone");
                    p.setStock(2000);
                    p.setPrice(10000);
                    return productRepository.save(p);
                });

        redisTemplate.opsForValue().set(
                STOCK_PREFIX + product.getId(),
                String.valueOf(product.getStock())
        );

        System.out.println(
                "Loaded Redis Key: " +
                        STOCK_PREFIX + product.getId() +
                        " -> " + product.getStock()
        );
    }

    public boolean attemptPurchase(String productId) {
        String key = STOCK_PREFIX + productId;
        Long remainingStock = redisTemplate.opsForValue().decrement(key);

        if(remainingStock != null && remainingStock >= 0) {
            return true;
        } else {
          redisTemplate.opsForValue().increment(key);
          return false;
        }
    }
}
