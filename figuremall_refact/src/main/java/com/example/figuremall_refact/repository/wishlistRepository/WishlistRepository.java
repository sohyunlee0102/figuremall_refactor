package com.example.figuremall_refact.repository.wishlistRepository;

import com.example.figuremall_refact.domain.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
