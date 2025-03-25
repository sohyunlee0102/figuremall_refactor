package com.example.figuremall_refact.dto.productDto;

import com.example.figuremall_refact.domain.category.Category;
import com.example.figuremall_refact.domain.product.ProductImage;
import com.example.figuremall_refact.domain.product.ProductOption;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        boolean isWishlisted;
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
        Category category;
        boolean isWishlisted;
        List<ProductOption> options;
        List<ProductImage> images;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionDto {
        ProductOption productOption;
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

}
