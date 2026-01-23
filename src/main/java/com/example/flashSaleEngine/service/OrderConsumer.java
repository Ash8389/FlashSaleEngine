package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.dto.OrderResponse;
import com.example.flashSaleEngine.model.Order;
import com.example.flashSaleEngine.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    @Autowired
    OrderRepository orderRepository;
    private static final String TOPIC = "flash-sale-orders";

    @KafkaListener(topics = TOPIC, groupId = "flash-sale-group")
    public void processOrder(OrderResponse orderResponse) {
        System.out.printf("CONSUMER: Received event for User %s. Saving to DB...%n", orderResponse.getUserId());

        Order order = new Order(orderResponse.getProductId(), orderResponse.getUserId());
        orderRepository.save(order);

        System.out.println("CONSUMER: Order Saved ID: " + order.getId());
    }
}
