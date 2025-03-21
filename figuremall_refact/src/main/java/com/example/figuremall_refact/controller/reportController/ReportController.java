package com.example.figuremall_refact.controller.reportController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.reportDto.ReportRequestDTO;
import com.example.figuremall_refact.dto.reportDto.ReportResponseDTO;
import com.example.figuremall_refact.service.reportService.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ApiResponse<ReportResponseDTO.CreateReportResponseDto> createReport(@Valid @RequestBody ReportRequestDTO.CreateReportDto request,
                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(reportService.createReport(request, userDetails.getUsername()));
    }

}
