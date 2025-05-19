package com.example.figuremall_refact.repository.cartRepository;

import com.example.figuremall_refact.domain.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByIdIn(List<Long> ids);
}
