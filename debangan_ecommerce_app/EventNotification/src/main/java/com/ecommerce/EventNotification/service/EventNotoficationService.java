package com.ecommerce.EventNotification.service;

import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventNotoficationService {

    @KafkaListener(topics = "order_created_topic", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderCreatedEvent(OrderCreatedEventDTO event) {
//        System.out.println("Received event: " + event);
        startCreditService(event.getOrderId(), event.getCustomerEmail(),event.getTotalAmount());
    }

    private void startCreditService(String orderId, String customerEmail,double total) {
        System.out.println("Event Notofication sent for: "+
                orderId + "\ntotal ="+total);
    }
}
