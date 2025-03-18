package com.example.figuremall_refact.service.inquiryService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.InquiryHandler;
import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.inquiryDto.InquiryRequestDTO;
import com.example.figuremall_refact.dto.inquiryDto.InquiryResponseDTO;
import com.example.figuremall_refact.repository.inqurityRepository.InquiryRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserService userService;

    public Inquiry findInquiryById(Long id) {
        return inquiryRepository.findById(id).orElseThrow(() -> new InquiryHandler(ErrorStatus.INQUIRY_NOT_FOUND));
    }

    @Transactional
    public InquiryResponseDTO.CreateInquiryResponseDto createInquiry(InquiryRequestDTO.CreateInquiryDto request, String email) {
        User user = userService.findByEmail(email);

        Inquiry inquiry = Inquiry.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return new InquiryResponseDTO.CreateInquiryResponseDto(inquiryRepository.save(inquiry).getId());
    }

    @Transactional
    public void deleteInquiry(InquiryRequestDTO.DeleteInquiryDto request) {
        inquiryRepository.deleteById(request.getInquiryId());
    }

}
