package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
