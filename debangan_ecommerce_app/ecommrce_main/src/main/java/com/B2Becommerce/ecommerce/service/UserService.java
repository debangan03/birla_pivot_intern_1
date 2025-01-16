package com.B2Becommerce.ecommerce.service;


import com.B2Becommerce.ecommerce.model.Order;
import com.B2Becommerce.ecommerce.model.User;
import com.B2Becommerce.ecommerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String login(User user) throws Exception{

        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
                        user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            throw new Exception("user not found");
        }
    }

    public User register(User user){

//        System.out.println(user.toString());
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Transactional
    public void addUserOrders(String userEmail, Order newOrder) throws Exception {
        // Fetch the user by email
        User userInDB = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new Exception("User not found with email: " + userEmail)
        );

        // Ensure the new order is not null
        if (newOrder == null) {
            throw new Exception("Order cannot be null or empty");
        }

        // Associate the order with the user
        newOrder.setUser(userInDB);

        // Add the order to the user's orders list (but do not save here)
        userInDB.getOrders().add(newOrder);
    }


    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }

}
