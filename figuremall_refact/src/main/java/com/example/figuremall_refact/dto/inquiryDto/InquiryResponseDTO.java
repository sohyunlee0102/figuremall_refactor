package com.example.figuremall_refact.dto.inquiryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InquiryResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateInquiryResponseDto {
        Long inquiryResponseId;
    }

}
