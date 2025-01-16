package com.B2Becommerce.ecommerce.service;


import com.B2Becommerce.ecommerce.model.User;
import com.B2Becommerce.ecommerce.model.UserPrincipal;
import com.B2Becommerce.ecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userRepo.findByEmail(username).orElse(null);


        if(user==null){
            System.out.println("User Not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
