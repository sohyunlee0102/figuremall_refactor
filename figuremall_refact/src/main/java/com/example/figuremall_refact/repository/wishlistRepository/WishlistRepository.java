package com.example.figuremall_refact.repository.wishlistRepository;

import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.domain.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByUserAndProduct(User user, Product product);
    Wishlist findByUserAndProduct(User user, Product product);
}
