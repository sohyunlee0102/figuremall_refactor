package com.example.figuremall_refact.service.productService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

    public ProductImage findById(Long id) {
        return productImageRepository.findById(id).orElseThrow(() -> new ProductHandler(ErrorStatus.PRODUCT_IMAGE_NOT_FOUND));
    }

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

    @Transactional
    public void updateProductImages(Product product, List<ProductRequestDTO.EditProductImageDTO> imageDTOS,
                                    List<Long> deleteImageIds, MultipartFile[] newFiles) {
        if (deleteImageIds != null) {
            deleteImages(deleteImageIds);
        }

        if (imageDTOS != null) {
            updateMainImageStatus(product, imageDTOS);
        }

        if (newFiles != null) {
            addNewImages(product, newFiles, imageDTOS);
        }
    }

    private void updateMainImageStatus(Product product, List<ProductRequestDTO.EditProductImageDTO> imageDTOS) {
        for (ProductRequestDTO.EditProductImageDTO imageDTO : imageDTOS) {
            if (Boolean.TRUE.equals(imageDTO.getIsMain())) {
                productImageRepository.findByProductAndIsMain(product, true)
                        .ifPresent(mainImage -> {
                            mainImage.setIsMain(false);
                            productImageRepository.save(mainImage);
                        });

                ProductImage newMainImage = findById(imageDTO.getImageId());
                newMainImage.setIsMain(true);
                productImageRepository.save(newMainImage);
            }
        }
    }

    private void deleteImages(List<Long> deleteImageIds) {
        List<ProductImage> productImages = productImageRepository.findAllById(deleteImageIds);
        for (ProductImage productImage : productImages) {
            s3Service.deleteFile(productImage.getImageUrl());
            productImageRepository.delete(productImage);
        }
    }

    private void addNewImages(Product product, MultipartFile[] files, List<ProductRequestDTO.EditProductImageDTO> imageDTOS) {
        Map<Integer, Boolean> metadataMap = imageDTOS.stream()
                .collect(Collectors.toMap(ProductRequestDTO.EditProductImageDTO::getIndex,
                        ProductRequestDTO.EditProductImageDTO::getIsMain, (a, b) -> b));

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) continue;

            boolean isMain = metadataMap.getOrDefault(i, false);

            if (isMain) {
                productImageRepository.findByProductAndIsMain(product, true)
                        .ifPresent(mainImage -> {
                            mainImage.setIsMain(false);
                            productImageRepository.save(mainImage);
                        });
            }

            try {
                String imageUrl = s3Service.uploadFile(file);

                ProductImage productImage = ProductImage.builder()
                        .product(product)
                        .imageUrl(imageUrl)
                        .isMain(isMain)
                        .build();

                productImageRepository.save(productImage);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }
    }

    @Transactional
    public String getMainImage(Product product) {
        return productImageRepository.findByProductAndIsMain(product, true).get().getImageUrl();
    }

}
