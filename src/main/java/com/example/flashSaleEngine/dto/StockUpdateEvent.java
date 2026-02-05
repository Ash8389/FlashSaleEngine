package com.example.flashSaleEngine.dto;

public class StockUpdateEvent {
    private String productId;
    private int remainingStock;
    private String message;

    public StockUpdateEvent() {
    }

    public StockUpdateEvent(String productId, int remainingStock, String message) {
        this.productId = productId;
        this.remainingStock = remainingStock;
        this.message = message;
    }
}
