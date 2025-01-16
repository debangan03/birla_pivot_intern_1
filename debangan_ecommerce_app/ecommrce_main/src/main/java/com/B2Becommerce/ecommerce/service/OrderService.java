package com.B2Becommerce.ecommerce.service;

import com.B2Becommerce.ecommerce.events.OrderCreatedEvent;
import com.B2Becommerce.ecommerce.events.OrderCreatedNotification;
import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.model.Product;
import com.B2Becommerce.ecommerce.repo.OrderRepo;
import com.B2Becommerce.ecommerce.repo.ProductRepo;
//import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import com.DTO.eventsDTO.models.OrderCreatedEventDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    private final ProductService productService;

    private final ProductRepo productRepo;

    private final PaymentService paymentService;

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    private final KafkaOrderEventPublisher kafkaOrderEventPublisher;

//    OrderCreatedEventDTO orderCreatedEventDTO;


    public double calculateSubtotal(List<Product> productsInCart) {
        return productsInCart.stream()
                .mapToDouble(productInCart -> {
                    // Fetch the product from the database
                    Product productFromDb = productRepo
                            .findById(productInCart.getId())
                            .orElse(null);

                    return productFromDb != null ? productFromDb.getPrice() : 0;

                })
                .sum();
    }


    @Transactional
    public Order processOrder(String email, Order order) throws Exception {
        // Validate and update quantities for all products in the order
        List<Product> products = order.getProducts();
        for (Product product : products) {
            productService.updateQty(product.getId());
        }

        // Associate the order with the user
        userService.addUserOrders(email, order);

        // Perform final validation and save the order
        return createOrder(email,order);
    }


    @Transactional
    public Order createOrder(String email,Order order) throws Exception {
        // Validate order details
        if (order == null || order.getProducts().isEmpty()) {
            throw new Exception("Order is empty or invalid.");
        }

        double calculatedTotal = calculateSubtotal(order.getProducts());
        if (calculatedTotal != order.getTotalAmount()) {
            throw new Exception("Order amount mismatch; cart tampered.");
        }

        // Save the order explicitly (only once here)
        Order newOrder = orderRepo.save(order);

        // Publish an event after the order is successfully created
        eventPublisher.publishEvent(new OrderCreatedEvent(newOrder));

        kafkaOrderEventPublisher
                .emitOrderCreatedEvent(new OrderCreatedEventDTO(newOrder.getId(),email, newOrder.getTotalAmount()));

        return newOrder;
    }


    public List<Order> getAll(){
        return orderRepo.findAll();
    }
}
