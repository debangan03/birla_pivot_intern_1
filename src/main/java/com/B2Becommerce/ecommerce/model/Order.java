package com.B2Becommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Use UUID for auto-generating unique IDs
    private String id;

    @Column(nullable = false)
    private double total_amount;

    @Column(nullable = false)
    private String payment_method;

    private String order_status;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)  // Enable cascading delete
    @JsonIgnoreProperties("orders")  // Prevent infinite recursion while serializing the data
    private List<Product> products;

    @ManyToMany(mappedBy = "orders")
    private List<User> users;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // Automatically set the created_at field
    }
}
