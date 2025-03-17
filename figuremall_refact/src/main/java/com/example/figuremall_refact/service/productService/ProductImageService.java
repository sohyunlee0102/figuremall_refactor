package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.apiPayload.exception.handler.ProductHandler;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.product.ProductImage;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.repository.productRepository.ProductImageRepository;
import com.example.figuremall_refact.service.s3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

    @Transactional
    public void saveProductImages(Product product, MultipartFile[] files, ProductRequestDTO.ImageInfoDto imageInfo) {
        Map<Integer, ProductRequestDTO.ImageMetadata> metadataMap = imageInfo.getImages().stream()
                .collect(Collectors.toMap(ProductRequestDTO.ImageMetadata::getIndex, metadata -> metadata));

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            ProductRequestDTO.ImageMetadata metadata = metadataMap.getOrDefault(i, new ProductRequestDTO.ImageMetadata(i, false));

            try {
                String imageUrl = s3Service.uploadFile(file);

                ProductImage productImage = ProductImage.builder()
                        .product(product)
                        .imageUrl(imageUrl)
                        .isMain(metadata.getIsMain())
                        .build();

                productImageRepository.save(productImage);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }
    }

}
