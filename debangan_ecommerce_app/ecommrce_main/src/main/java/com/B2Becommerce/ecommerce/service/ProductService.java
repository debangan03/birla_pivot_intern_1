package com.B2Becommerce.ecommerce.service;

import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.model.Product;
import com.B2Becommerce.ecommerce.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
public class ProductService {

    private static final Logger log = LogManager.getLogger(ProductService.class);
    @Autowired
    private ProductRepo productRepo;


    public List<Product> getAllProducts(){
        try{
            return productRepo.findAll();
        } catch (Exception e) {
            log.error("error: ", e);
            throw new RuntimeException("exception occurred");
        }

    }

    public void addProducts(List<Product> products){
        try{
            products.forEach(product -> productRepo.save(product));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProduct(String id){
        try {
            Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
            // Remove product from any orders before deleting it
            for (Order order : product.getOrders()) {
                order.getProducts().remove(product);  // Remove this product from the orders
            }
            // Now delete the product
            productRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Unable to delete product with id: " + id, e);
            throw new RuntimeException("Unable to delete product", e);
        }
    }



    public Product updateProduct(String id, Product product) {
        try {
            // Fetch the product from the database by ID
            Product productInDb = productRepo.findById(id).orElse(null);

            if (productInDb == null) {
                throw new Exception("No Product found with the id: " + id);
            }

            // Update attributes if they are not null
            if (product.getPrice() != 0) {
                productInDb.setPrice(product.getPrice());
            }

            if (product.getQty() != 0) {
                productInDb.setQty(product.getQty());
            }

            if (product.getDescription() != null) {
                productInDb.setDescription(product.getDescription());
            }

            // Save the updated product back to the repository
            return productRepo.save(productInDb);

        } catch (Exception e) {
            log.error("Unable to update product with id: {}", id, e);
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    @Transactional
    public Product updateQty(String id) throws Exception {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new Exception("Product not found: " + id));

        if (product.getQty() < 1) {
            throw new Exception("Product is out of stock: " + id);
        }

        product.setQty(product.getQty() - 1);
        return productRepo.save(product);
    }




}
