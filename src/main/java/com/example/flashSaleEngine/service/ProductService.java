package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.dto.DtoMapper;
import com.example.flashSaleEngine.dto.ProductDto;
import com.example.flashSaleEngine.model.Product;
import com.example.flashSaleEngine.repository.ProductRepository;
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

    //    @PostConstruct
//    public void init() {
//
//        String productId = "IPHONE_15_PRO";
//
//        Product product = productRepository.findById(productId)
//                .orElseGet(() -> {
//                    Product p = new Product();
//                    p.setId(productId);
//                    p.setName("iPhone");
//                    p.setStock(2000);
//                    p.setPrice(10000);
//                    return productRepository.save(p);
//                });
//
//        redisTemplate.opsForValue().set(
//                STOCK_PREFIX + product.getId(),
//                String.valueOf(product.getStock())
//        );
//
//        System.out.println(
//                "Loaded Redis Key: " +
//                        STOCK_PREFIX + product.getId() +
//                        " -> " + product.getStock()
//        );
//    }
    public ProductDto getProduct(String id) {
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id).orElseThrow();

            redisTemplate.opsForValue().set(
                    STOCK_PREFIX + product.getId(),
                    String.valueOf(product.getStock())
            );

            System.out.println(
                    "Loaded Redis Key: " +
                            STOCK_PREFIX + product.getId() +
                            " -> " + product.getStock()
            );

            DtoMapper dtoMapper = new DtoMapper();
            return dtoMapper.productToProductDto(product);
        }

        return null;
    }

    public ProductDto addProduct(Product product) {
        if (product == null)
            return null;
        Product savedProduct = productRepository.save(product);

        DtoMapper dtoMapper = new DtoMapper();

        return dtoMapper.productToProductDto(savedProduct);
    }

    public ProductDto update(String id, Product product) {
        if (productRepository.existsById(id)) {
            Product preSaved = productRepository.findById(id).orElseThrow();

            if (product != null) {
                if (product.getName() != null && !product.getName().isEmpty()) {
                    preSaved.setName(product.getName());
                }
                if (product.getDescription() != null && !product.getDescription().isEmpty()) {
                    preSaved.setDescription(product.getDescription());
                }
                preSaved.setStock(product.getStock());
                preSaved.setPrice(product.getPrice());
            }
            DtoMapper dtoMapper = new DtoMapper();
            return dtoMapper.productToProductDto(productRepository.save(preSaved));
        }

        return null;
    }

    public boolean delete(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public int attemptPurchase(String productId) {
        String key = STOCK_PREFIX + productId;
        Long remainingStock = redisTemplate.opsForValue().decrement(key);

        if (remainingStock != null && remainingStock >= 0) {
            return remainingStock.intValue();
        } else {
            redisTemplate.opsForValue().increment(key);
            return -1;
        }
    }

}