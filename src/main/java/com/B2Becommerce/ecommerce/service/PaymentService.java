package com.B2Becommerce.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    public void initiateOnlinePayment(double amount){
        System.out.println("gateway to payment");


    }

    public void ProcessCashPayment(double amount){
        System.out.println("payment processed");



    }
}
