package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.dto.StockUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockUpdatePublisher {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void publish(String productId, int stock) {
        StockUpdateEvent event = new StockUpdateEvent(
                productId,
                stock,
                stock > 0 ? "Stock Updated" : "Out of Stock"
        );

        messagingTemplate.convertAndSend(
                "/topic/stock" + productId,
                event
        );
    }

}
