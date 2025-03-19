package com.example.figuremall_refact.service.inquiryService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.InquiryHandler;
import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.inquiry.InquiryResponse;
import com.example.figuremall_refact.dto.inquiryDto.InquiryRequestDTO;
import com.example.figuremall_refact.dto.inquiryDto.InquiryResponseDTO;
import com.example.figuremall_refact.repository.inqurityRepository.InquiryResponseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryResponseService {

    private final InquiryResponseRepository inquiryResponseRepository;
    private final InquiryService inquiryService;

    public InquiryResponse findById(Long id) {
        return inquiryResponseRepository.findById(id).orElseThrow(() -> new InquiryHandler(ErrorStatus.INQUIRY_RESPONSE_NOT_FOUND));
    }

    @Transactional
    public InquiryResponseDTO.CreateInquiryResponseDto createInquiryResponse(InquiryRequestDTO.AddInquiryResponseDto request) {
        Inquiry inquiry = inquiryService.findInquiryById(request.getInquiryId());

        InquiryResponse inquiryResponse = InquiryResponse.builder()
                .inquiry(inquiry)
                .content(request.getContent())
                .build();

        return new InquiryResponseDTO.CreateInquiryResponseDto(inquiryResponse.getId());
    }

}
