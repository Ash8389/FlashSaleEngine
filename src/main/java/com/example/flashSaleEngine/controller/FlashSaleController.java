package com.example.flashSaleEngine.controller;

import com.example.flashSaleEngine.dto.OrderResponse;
import com.example.flashSaleEngine.service.OrderProducer;
import com.example.flashSaleEngine.service.ProductService;
import com.example.flashSaleEngine.service.RateLimitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flash-sale")
public class FlashSaleController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private RateLimitingService rateLimitingService;

    @GetMapping("/{productId}")
    public ResponseEntity<String> buyProduct(@PathVariable String productId){
        boolean success = productService.attemptPurchase(productId);

        if(success){
            return ResponseEntity.ok("ORDER ACCEPTED: Added to cart!");
        }else{
            return ResponseEntity.status(400).body("SOLD OUT!");
        }
    }

    @PostMapping("/buy/{productId}/{userId}")
    public ResponseEntity<String> buyProduct(@PathVariable String productId, @PathVariable String userId) {

        if (!rateLimitingService.tryAccess(userId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("SLOW DOWN! Too many requests.");
        }

        boolean success = productService.attemptPurchase(productId);

        if (success) {
            OrderResponse event = new OrderResponse(userId, productId);
            orderProducer.sendOrderResponse(event);

            return ResponseEntity.ok("Request Accepted! You will receive a confirmation shortly.");
        } else {
            return ResponseEntity.status(400).body("SOLD OUT");
        }
    }
}
