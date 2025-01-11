package com.B2Becommerce.ecommerce.service;


import com.B2Becommerce.ecommerce.model.User;
import com.B2Becommerce.ecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String login(User user) throws Exception{
        User userInDb = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(userInDb==null){
            throw new Exception("User not found in db");
        }
        //match password
        if(user.getPassword().equals(userInDb.getPassword())){
            return "jwtcode";

        }
        else{
            throw new Exception("Credentials mismatch");
        }


    }

    public void register(User user){
        userRepo.save(user);
    }

}
