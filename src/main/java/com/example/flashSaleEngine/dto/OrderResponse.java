package com.example.flashSaleEngine.dto;

public class OrderResponse {
    private String productId;
    private String userId;
    public OrderResponse(String userId, String productId) {
        this.productId = productId;
        this.userId = userId;
    }

    public OrderResponse() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
