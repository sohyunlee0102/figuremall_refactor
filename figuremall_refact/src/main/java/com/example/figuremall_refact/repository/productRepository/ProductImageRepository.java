package com.example.figuremall_refact.repository.productRepository;

import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByProductAndIsMain(Product product, Boolean isMain);
    List<ProductImage> findAllByIdIn(List<Long> ids);
}
