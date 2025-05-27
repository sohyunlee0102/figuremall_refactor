package com.example.figuremall_refact.dto.inquiryDto;

import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class InquiryRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateInquiryDto {

        @NotBlank
        String title;
        @NotBlank
        String content;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddInquiryResponseDto {

        @NotNull
        Long inquiryId;
        @NotBlank
        String content;

    }

}
