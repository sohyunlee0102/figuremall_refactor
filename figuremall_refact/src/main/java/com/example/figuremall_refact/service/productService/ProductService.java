package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.dto.productDto.ProductResponseDTO;
import com.example.figuremall_refact.repository.productRepository.ProductRepository;
import com.example.figuremall_refact.service.s3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionService productOptionService;
    private final ProductImageService productImageService;

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public ProductResponseDTO.AddProductResponseDto addProduct(
            ProductRequestDTO.AddProductDto productInfo, MultipartFile[] files,
            ProductRequestDTO.ImageInfoDto imageInfo) {
        Product product = Product.builder()
                .name(productInfo.getName())
                .price(productInfo.getPrice())
                .likeCount(0)
                .description(productInfo.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        productOptionService.saveProductOptions(product, productInfo.getOptions());
        productImageService.saveProductImages(product, files, imageInfo);

        return new ProductResponseDTO.AddProductResponseDto(savedProduct.getId());
    }

}
