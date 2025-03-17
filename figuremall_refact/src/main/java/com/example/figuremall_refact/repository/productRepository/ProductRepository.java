package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
