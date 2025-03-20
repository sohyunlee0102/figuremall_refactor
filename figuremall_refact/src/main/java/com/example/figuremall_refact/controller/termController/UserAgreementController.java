package com.example.figuremall_refact.controller.termController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.termDto.UserAgreementRequestDTO;
import com.example.figuremall_refact.dto.termDto.UserAgreementResponseDTO;
import com.example.figuremall_refact.service.termsService.UserAgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAgreements")
public class UserAgreementController {

    private final UserAgreementService userAgreementService;

    @PostMapping
    public ApiResponse<UserAgreementResponseDTO> addUserAgreement(@Valid @RequestBody UserAgreementRequestDTO.UserAgreementDto request,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(userAgreementService.addAgreement(request, userDetails.getUsername()));
    }

    @DeleteMapping
    public ApiResponse<String> deleteUserAgreement(@Valid @RequestBody UserAgreementRequestDTO.DeleteAgreementDto request) {
        userAgreementService.deleteUserAgreement(request);
        return ApiResponse.onSuccess("약관 동의 기록이 삭제되었습니다.");
    }

}
