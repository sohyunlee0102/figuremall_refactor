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

    @DeleteMapping("/{inquiryId}")
    public ApiResponse<String> deleteInquiry(@PathVariable("inquiryId") Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return ApiResponse.onSuccess("문의가 삭제되었습니다.");
    }

    @PostMapping("/response")
    public ApiResponse<InquiryResponseDTO.CreateInquiryResponseDto> createInquiryResponse(@Valid @RequestBody
                                                                                              InquiryRequestDTO.AddInquiryResponseDto request) {
        return ApiResponse.onSuccess(inquiryResponseService.createInquiryResponse(request));
    }

    @GetMapping
    public ApiResponse<InquiryResponseDTO.InquiryPageResponse> getInquiries(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(inquiryService.getInquiries(userDetails.getUsername(), page));
    }

    @GetMapping("/{inquiryId}")
    public ApiResponse<InquiryResponseDTO.InquiryDetail> getInquiry(@PathVariable("inquiryId") Long inquiryId) {
        return ApiResponse.onSuccess(inquiryService.getInquiryDetail(inquiryId));
    }

}
