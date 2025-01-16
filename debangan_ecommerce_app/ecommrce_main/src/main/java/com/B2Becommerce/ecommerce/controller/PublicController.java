package com.B2Becommerce.ecommerce.controller;

import com.B2Becommerce.ecommerce.model.User;
import com.B2Becommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;




    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){

        try {
            String jwtToken = userService.login(user);
            if(jwtToken==null){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(jwtToken,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        try {
            User newUser = userService.register(user);
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getallusers")
    public ResponseEntity<?> getAll(){
        List<User> usrs = userService.getAll();
        return new ResponseEntity<>(usrs,HttpStatus.OK);
    }

}
