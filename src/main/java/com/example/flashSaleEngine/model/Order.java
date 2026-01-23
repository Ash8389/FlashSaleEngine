package com.example.flashSaleEngine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "orders")
public class Order {
    @Id
    private String id = UUID.randomUUID().toString();
    private String productId;
    private String userId;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public Order(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.status = OrderStatus.ACCEPTED;
        this.orderDate = LocalDateTime.now();
    }
    public Order() {}

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
