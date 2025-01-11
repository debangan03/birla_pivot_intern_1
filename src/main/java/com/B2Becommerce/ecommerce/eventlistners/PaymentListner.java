package com.B2Becommerce.ecommerce.eventlistners;


import com.B2Becommerce.ecommerce.events.OrderCreatedEvent;
import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentListner {

    @Autowired
    private PaymentService paymentService;


    //@EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleOrderCreatedEvent(OrderCreatedEvent event){
        Order currentOrder = event.getOrder();

        String orderId = currentOrder.getId();
        double totalAmount = currentOrder.getTotal_amount();

        try {
            if (currentOrder.getPayment_method().equalsIgnoreCase("cash")) {
                paymentService.ProcessCashPayment(totalAmount);
                System.out.println("Cash payment processed successfully for order: " + orderId);

            } else if (currentOrder.getPayment_method().equalsIgnoreCase("online")) {
                paymentService.initiateOnlinePayment(totalAmount);
                System.out.println("Online payment initiated successfully for order: " + orderId);
            }
        } catch (Exception e) {
            System.err.println("Payment processing failed for order: " + orderId + ". Error: " + e.getMessage());
        }

    }
}
