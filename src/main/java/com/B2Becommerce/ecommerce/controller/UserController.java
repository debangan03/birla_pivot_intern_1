package com.B2Becommerce.ecommerce.controller;


import com.B2Becommerce.ecommerce.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);


    }

}
