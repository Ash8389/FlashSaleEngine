package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "flash-sale-orders";

    public void sendOrderResponse(OrderResponse order) {
        System.out.println("PRODUCER: Sending order for User %d -> Product %d%n" + order.getUserId() + order.getProductId());

        kafkaTemplate.send(TOPIC, order);
    }
}
