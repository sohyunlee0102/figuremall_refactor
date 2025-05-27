package com.example.figuremall_refact.dto.inquiryDto;

import com.example.figuremall_refact.domain.enums.InquiryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateInquiryResponseDto {
        Long inquiryResponseId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryList {
        Long id;
        String title;
        InquiryStatus status;
        LocalDateTime createTime;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryDetail {
        String title;
        String content;
        InquiryStatus status;
        LocalDateTime createTime;
        InquiryResponse response;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryResponse {
        Long id;
        String content;
        LocalDateTime createTime;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryPageResponse {
        List<InquiryList> inquiries;
        Integer currentPage;
        Integer totalPages;
        Long totalElements;

    }

}
