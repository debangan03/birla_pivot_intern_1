package com.B2Becommerce.ecommerce.repo;

import com.B2Becommerce.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
