package com.B2Becommerce.ecommerce.controller;


import com.B2Becommerce.ecommerce.model.User;
import com.B2Becommerce.ecommerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody User user){

        try {

            String jwtToken = userService.login(user);
           // System.out.println(jwtToken);


            if(jwtToken!=null){
                return new ResponseEntity<>(jwtToken, HttpStatus.OK);

            }else{
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("e: {}", e.getMessage());
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        try {
            userService.register(user);
            return new ResponseEntity<>(true, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("e: {}", e.getMessage());
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

}
