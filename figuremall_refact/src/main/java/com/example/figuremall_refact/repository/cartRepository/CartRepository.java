package com.example.figuremall_refact.repository.cartRepository;

import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
