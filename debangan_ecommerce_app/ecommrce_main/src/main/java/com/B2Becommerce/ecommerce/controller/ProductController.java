package com.B2Becommerce.ecommerce.controller;

import com.B2Becommerce.ecommerce.model.Product;
import com.B2Becommerce.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        try{
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products,
                    products!=null?HttpStatus.OK:HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }
    @PostMapping
    public ResponseEntity<?> addProducts(@RequestBody List<Product> products){
        try{
            productService.addProducts(products);
            return new ResponseEntity<>("successfully added",
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,
                                           @RequestBody Product product) {
        try {
            productService.updateProduct(id,product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
