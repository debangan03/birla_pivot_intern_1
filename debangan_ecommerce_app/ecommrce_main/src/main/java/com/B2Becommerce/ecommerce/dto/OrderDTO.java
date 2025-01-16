package com.B2Becommerce.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private String id;                   // Order ID
    private double totalAmount;          // Total order amount
    private String paymentMethod;        // Payment method (e.g., Credit Card, PayPal)
    private String orderStatus;          // Order status as a string (e.g., PENDING, COMPLETED)
    private LocalDateTime createdAt;     // Order creation timestamp
    private List<ProductDTO> products;   // List of products associated with the order

    // Nested DTO for product information
    @Data
    @Builder
    public static class ProductDTO {
        private String id;               // Product ID
        private String description;      // Product description
        private double price;            // Product price
        private int qty;                 // Quantity of the product
    }
}
