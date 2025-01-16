package com.B2Becommerce.ecommerce.repo;

import com.B2Becommerce.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {

}
