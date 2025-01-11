package com.B2Becommerce.ecommerce.service;

import com.B2Becommerce.ecommerce.events.OrderCreatedEvent;
import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.model.Product;
import com.B2Becommerce.ecommerce.repo.OrderRepo;
import com.B2Becommerce.ecommerce.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;


    @Autowired
    private ApplicationEventPublisher eventPublisher;


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
    public Order processOrder(Order order) throws Exception {
        List<Product> products = order.getProducts();

        // Update quantities for all products in the order
        for (Product product : products) {

            productService.updateQty(product.getId());
        }

        // Create the order
        return createOrder(order);
    }
    

    public Order createOrder(Order order){
        try{
            if(order!=null) {
                List<Product> productsInCart = order.getProducts();
                if(productsInCart.isEmpty()){
                    throw new Exception("empty order");
                }
                double calculatedTotal = calculateSubtotal(productsInCart);

                if(calculatedTotal!=order.getTotal_amount()){
                    throw new Exception("Order amount mismatch cart tampered");
                }

//                if(order.getPayment_method().equalsIgnoreCase("cash")){
//                    System.out.println("order successful");
//                    paymentService.ProcessCashPayment(calculatedTotal);
//
//
//                }else if(order.getPayment_method()
//                        .equalsIgnoreCase("online")){
//                            paymentService
//                                    .initiateOnlinePayment(calculatedTotal);
//                }


                //order.setOrder_status("placed");
                Order newOrder = orderRepo.save(order);

                //emit order created event
                eventPublisher.publishEvent(new OrderCreatedEvent(newOrder));
                return newOrder;

            }else{
                throw new Exception("empty order");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public List<Order> getAll(){
        return orderRepo.findAll();
    }


}
