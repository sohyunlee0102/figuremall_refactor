package com.example.figuremall_refact.dto.reviewDto;

import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReviewRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddReviewDto {

        @NotNull
        Long productId;
        @NotBlank
        String content;
        @NotNull
        Float rating;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReviewDto {

        Long reviewId;
        String content;
        Float rating;
        List<Long> deleteImageIds;
        
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteReviewDto {

        @NotNull
        Long reviewId;

    }

}