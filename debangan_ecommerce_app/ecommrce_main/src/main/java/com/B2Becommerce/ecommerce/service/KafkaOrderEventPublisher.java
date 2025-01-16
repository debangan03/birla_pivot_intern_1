package com.B2Becommerce.ecommerce.service;


//import com.B2Becommerce.ecommerce.events.OrderCreatedEvent;
import com.B2Becommerce.ecommerce.events.OrderCreatedNotification;
import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEventDTO> kafkaTemplate;

//    @Value("${kafka.topic.order-created}")
    private String topic = "order_created_topic";

    public KafkaOrderEventPublisher(KafkaTemplate<String, OrderCreatedEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void emitOrderCreatedEvent(OrderCreatedEventDTO event) {
        kafkaTemplate.send(topic, event);
    }
}
