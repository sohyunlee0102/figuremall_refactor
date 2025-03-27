package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Slice<Product> findAllByOrderByLikeCountDesc(Pageable pageable);
}
