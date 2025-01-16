package com.B2Becommerce.ecommerce.repo;

import com.B2Becommerce.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {

}