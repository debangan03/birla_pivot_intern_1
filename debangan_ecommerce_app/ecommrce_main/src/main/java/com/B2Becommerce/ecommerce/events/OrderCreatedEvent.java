package com.B2Becommerce.ecommerce.events;

import com.B2Becommerce.ecommerce.model.Order;
import lombok.Getter;

@Getter
public class OrderCreatedEvent {
    private final Order order;

    public OrderCreatedEvent(Order order){
        this.order=order;

    }
}
