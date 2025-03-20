package com.example.figuremall_refact.service.termsService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.TermHandler;
import com.example.figuremall_refact.domain.term.Term;
import com.example.figuremall_refact.domain.term.UserAgreement;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.termDto.UserAgreementRequestDTO;
import com.example.figuremall_refact.dto.termDto.UserAgreementResponseDTO;
import com.example.figuremall_refact.repository.termRepository.UserAgreementRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAgreementService {

    private final UserAgreementRepository userAgreementRepository;
    private final UserService userService;
    private final TermService termService;

    public UserAgreement findById(Long id) {
        return userAgreementRepository.findById(id).orElseThrow(() -> new TermHandler(ErrorStatus.USER_AGREEMENT_NOT_FOUND));
    }

    @Transactional
    public UserAgreementResponseDTO addAgreement(UserAgreementRequestDTO.UserAgreementDto request, String email) {
        User user = userService.findByEmail(email);
        Term term = termService.findById(request.getTermId());

        UserAgreement userAgreement = UserAgreement.builder()
                .user(user)
                .term(term)
                .build();

        return new UserAgreementResponseDTO(userAgreement.getId());
    }

    @Transactional
    public void deleteUserAgreement(UserAgreementRequestDTO.DeleteAgreementDto request) {
        userAgreementRepository.deleteById(request.getUserAgreementId());
    }
}
