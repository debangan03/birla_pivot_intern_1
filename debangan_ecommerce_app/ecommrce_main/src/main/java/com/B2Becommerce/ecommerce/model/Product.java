package com.B2Becommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Use UUID for auto-generating unique IDs
    private String id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false,name = "available_qty")
    private int qty;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products") // Reference the field name in the Order entity
    @JsonIgnoreProperties("products") // Prevent infinite recursion
    private List<Order> orders;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // Automatically set the created_at field
    }
}
