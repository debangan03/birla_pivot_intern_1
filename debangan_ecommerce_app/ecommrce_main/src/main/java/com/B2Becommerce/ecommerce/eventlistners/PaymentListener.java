package com.B2Becommerce.ecommerce.eventlistners;

import com.B2Becommerce.ecommerce.events.OrderCreatedEvent;
import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentListener {

    @Autowired
    private PaymentService paymentService;

//    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        Order order = event.getOrder();
        double totalAmount = order.getTotalAmount();

        try {
            if (order.getPaymentMethod().equalsIgnoreCase("cash")) {
                paymentService.ProcessCashPayment(totalAmount);
                System.out.println("Cash payment processed successfully for order: " + order.getId());
            } else if (order.getPaymentMethod().equalsIgnoreCase("online")) {
                paymentService.initiateOnlinePayment(totalAmount);
                System.out.println("Online payment initiated successfully for order: " + order.getId());
            }
        } catch (Exception e) {
            System.err.println("Payment processing failed for order: " + order.getId() + ". Error: " + e.getMessage());
        }
    }
}
