package com.example.figuremall_refact.dto.productDto;

import com.example.figuremall_refact.domain.category.Category;
import com.example.figuremall_refact.domain.product.ProductImage;
import com.example.figuremall_refact.domain.product.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddProductResponseDto {
        Long productId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeResponseDto {
        Long productId;
        Integer price;
        String name;
        Integer likeCount;
        Long wishlistId;
        String mainImageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDto {
        Long productId;
        Integer price;
        Float rating;
        String name;
        String description;
        Long categoryId;
        String categoryName;
        Long wishlistId;
        List<ProductOptionDto> options;
        List<ProductImageDto> images;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionDto {
        Long optionId;
        String optionName;
        List<ProductOptionValueDto> values;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionValueDto {
        Long valueId;
        String valueName;
        Long extraPrice;
        boolean isSoldOut;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageDto {
        Long imageId;
        String imageUrl;
        boolean isMain;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductListDto {
        Long id;
        String name;
        LocalDateTime createdAt;
    }

}
