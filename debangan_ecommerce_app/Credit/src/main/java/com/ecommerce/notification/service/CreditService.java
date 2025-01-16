package com.ecommerce.notification.service;

import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreditService {
    @KafkaListener(topics = "order_created_topic", groupId = "credit-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderCreatedEvent(OrderCreatedEventDTO event) {
//        System.out.println("Received event: " + event);
        startCreditService(event.getOrderId(), event.getCustomerEmail(),event.getTotalAmount());
    }

    private void startCreditService(String orderId, String customerEmail,double total) {
        System.out.println("Credit service started for Order: "+
                orderId + "\ntotal ="+total);
    }
}
