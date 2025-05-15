package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop7ByOrderByLikeCountDesc();
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
