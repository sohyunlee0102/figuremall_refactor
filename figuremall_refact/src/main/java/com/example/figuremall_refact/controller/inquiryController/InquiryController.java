package com.example.figuremall_refact.controller.inquiryController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.domain.inquiry.InquiryResponse;
import com.example.figuremall_refact.dto.inquiryDto.InquiryRequestDTO;
import com.example.figuremall_refact.dto.inquiryDto.InquiryResponseDTO;
import com.example.figuremall_refact.service.inquiryService.InquiryResponseService;
import com.example.figuremall_refact.service.inquiryService.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;
    private final InquiryResponseService inquiryResponseService;

    @PostMapping
    public ApiResponse<InquiryResponseDTO.CreateInquiryResponseDto> createInquiry(@Valid @RequestBody InquiryRequestDTO.CreateInquiryDto request,
                                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(inquiryService.createInquiry(request, userDetails.getUsername()));
    }

    @DeleteMapping
    public ApiResponse<String> deleteInquiry(@Valid @RequestBody InquiryRequestDTO.DeleteInquiryDto request) {
        inquiryService.deleteInquiry(request);
        return ApiResponse.onSuccess("문의가 삭제되었습니다.");
    }

    @PostMapping("/response")
    public ApiResponse<InquiryResponseDTO.CreateInquiryResponseDto> createInquiryResponse(@Valid @RequestBody
                                                                                              InquiryRequestDTO.AddInquiryResponseDto request) {
        return ApiResponse.onSuccess(inquiryResponseService.createInquiryResponse(request));
    }

}
