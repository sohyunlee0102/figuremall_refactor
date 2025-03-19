package com.example.figuremall_refact.repository.cartRepository;

import com.example.figuremall_refact.domain.cart.CartItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemOptionRepository extends JpaRepository<CartItemOption, Long> {
}
