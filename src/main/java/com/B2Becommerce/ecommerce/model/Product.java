package com.B2Becommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties("products") // Prevent infinite recursion while serializing the data
    private List<Order> orders; // List of orders containing this product

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // Automatically set the created_at field
    }
}
