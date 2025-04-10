package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.ProductOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue, Long> {

}
