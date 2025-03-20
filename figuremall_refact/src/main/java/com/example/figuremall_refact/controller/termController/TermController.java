package com.example.figuremall_refact.controller.termController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.termDto.TermRequestDTO;
import com.example.figuremall_refact.dto.termDto.TermResponseDTO;
import com.example.figuremall_refact.service.termsService.TermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermController {

    private final TermService termService;

    @PostMapping
    public ApiResponse<TermResponseDTO.CreateTermResponseDto> createTerm(@Valid @RequestBody TermRequestDTO.CreateTermDto request) {
        return ApiResponse.onSuccess(termService.createTerm(request));
    }

}
