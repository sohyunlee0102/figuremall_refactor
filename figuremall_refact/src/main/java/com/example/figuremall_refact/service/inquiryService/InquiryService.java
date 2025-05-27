package com.example.figuremall_refact.service.inquiryService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.InquiryHandler;
import com.example.figuremall_refact.domain.enums.InquiryStatus;
import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.inquiry.InquiryResponse;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.inquiryDto.InquiryRequestDTO;
import com.example.figuremall_refact.dto.inquiryDto.InquiryResponseDTO;
import com.example.figuremall_refact.repository.inqurityRepository.InquiryRepository;
import com.example.figuremall_refact.repository.inqurityRepository.InquiryResponseRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserService userService;
    private final InquiryResponseRepository inquiryResponseRepository;

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
                .inquiryStatus(InquiryStatus.UNANSWERED)
                .build();

        return new InquiryResponseDTO.CreateInquiryResponseDto(inquiryRepository.save(inquiry).getId());
    }

    @Transactional
    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }

    @Transactional
    public InquiryResponseDTO.InquiryPageResponse getInquiries(String email, Integer page) {
        User user = userService.findByEmail(email);
        Integer pageSize = 5;

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Inquiry> inquiryPage = inquiryRepository.findByUser(user, pageable);

        List<InquiryResponseDTO.InquiryList> dtoList = inquiryPage.getContent().stream()
                .map(inquiry -> new InquiryResponseDTO.InquiryList(
                        inquiry.getId(), inquiry.getTitle(), inquiry.getInquiryStatus(), inquiry.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new InquiryResponseDTO.InquiryPageResponse(dtoList, inquiryPage.getNumber(), inquiryPage.getTotalPages(),
                inquiryPage.getTotalElements());
    }

    @Transactional
    public InquiryResponseDTO.InquiryDetail getInquiryDetail(Long inquiryId) {
        Inquiry inquiry = findInquiryById(inquiryId);
        InquiryResponse response = inquiryResponseRepository.findByInquiryId(inquiryId);
        InquiryResponseDTO.InquiryResponse dto = new InquiryResponseDTO.InquiryResponse(response.getId(), response.getContent(),
                response.getCreatedAt());

        return new InquiryResponseDTO.InquiryDetail(inquiry.getTitle(), inquiry.getContent(), inquiry.getInquiryStatus(),
                inquiry.getCreatedAt(), dto);
    }

}
