package com.example.figuremall_refact.dto.reportDto;

import com.example.figuremall_refact.domain.enums.ReportReason;
import com.example.figuremall_refact.domain.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReportDto {

        @NotNull
        ReportType reportType;
        @NotNull
        ReportReason reportReason;
        String details;
        Long postId;
        Long commentId;

    }

}
