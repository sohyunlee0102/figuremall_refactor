package com.example.figuremall_refact.dto.productDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProductRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddProductDto {

        @NotBlank
        String name;
        @NotNull
        Integer price;
        String description;
        @NotNull
        List<ProductOptionDTO> options;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionDTO {
        @NotBlank
        String optionName;
        @NotNull
        List<ProductOptionValueDTO> values;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionValueDTO {
        @NotBlank
        String valueName;
        @NotNull
        Long extraPrice;
        @NotNull
        Long stock;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageInfoDto {
        @NotNull
        List<ImageMetadata> images;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageMetadata {
        @NotNull
        Integer index;  // 이미지 배열의 인덱스
        @NotNull
        Boolean isMain; // 메인 이미지 여부
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditProductDto {

        @NotBlank
        String name;
        @NotNull
        Integer price;
        String description;
        @NotNull
        List<ProductOptionDTO> options;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteProductDto {

        @NotNull
        Long productId;

    }

}
