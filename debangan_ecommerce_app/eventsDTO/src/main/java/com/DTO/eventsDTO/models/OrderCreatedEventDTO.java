package com.DTO.eventsDTO.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEventDTO {

    private String orderId;
    private String  customerEmail;
    private double totalAmount;

    @Override
    public String toString() {
        return "OrderCreatedEventDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }



}
