package com.example.flashSaleEngine.controller;

import com.example.flashSaleEngine.dto.OrderResponse;
import com.example.flashSaleEngine.dto.ProductDto;
import com.example.flashSaleEngine.model.Product;
import com.example.flashSaleEngine.model.User;
import com.example.flashSaleEngine.security.model.CustomUserDetails;
import com.example.flashSaleEngine.service.OrderProducer;
import com.example.flashSaleEngine.service.ProductService;
import com.example.flashSaleEngine.service.RateLimitingService;
import com.example.flashSaleEngine.service.StockUpdatePublisher;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RateLimitingService rateLimitingService;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private StockUpdatePublisher stockUpdatePublisher;

    @Operation(
            summary = "Place an order",
            description = "Allows an authenticated user to place an order for a product during flash sale"
    )
    @PostMapping("/buy/{proId}")
    public ResponseEntity<String> buyProduct(@PathVariable String proId, @AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();
        if(!rateLimitingService.tryAccess(user.getId())){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("So many attempts, try after sometimes!!");
        }

        int stockLeft = productService.attemptPurchase(proId);
        if(stockLeft>=0){
            stockUpdatePublisher.publish(proId, stockLeft);
            OrderResponse orderResponse = new OrderResponse(user.getId(), proId);
            orderProducer.sendOrderResponse(orderResponse);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success!!");
        }

        return ResponseEntity.status(400).body("Out Of Stock!!");
    }

    @Operation(
            summary = "Get product by id",
            description = "Allows an authenticated user to get product by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id){
        ProductDto product = productService.getProduct(id);
        if(product != null){
            return ResponseEntity.ok().body(product);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Add Product",
            description = "Allows an authenticated user to add product"
    )
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product){
        ProductDto savedProduct = productService.addProduct(product);
        if(savedProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Update product",
            description = "Allows an authenticated user to update product."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody Product product) {
        ProductDto savedProduct = productService.update(id, product);
        if (savedProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @Operation(
            summary = "Delete product",
            description = "Allows an authenticated user to delete product."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        boolean deleted = productService.delete(id);

        if(deleted){
            return ResponseEntity.ok().body("User with id: %s" + id + " deleted.");
        }

        return ResponseEntity.notFound().build();
    }
}
