package com.example.flashSaleEngine.controller;

import com.example.flashSaleEngine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flash-sale")
public class FlashSaleController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<String> buyProduct(@PathVariable String productId){
        boolean success = productService.attemptPurchase(productId);

        if(success){
            return ResponseEntity.ok("ORDER ACCEPTED: Added to cart!");
        }else{
            return ResponseEntity.status(400).body("SOLD OUT!");
        }
    }
}
