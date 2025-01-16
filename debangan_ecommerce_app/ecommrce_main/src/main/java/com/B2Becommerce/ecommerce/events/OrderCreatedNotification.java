package com.B2Becommerce.ecommerce.events;

import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedNotification {
    private String orderId;
    private String customerEmail;


}
