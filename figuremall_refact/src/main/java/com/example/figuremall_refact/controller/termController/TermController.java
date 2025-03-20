package com.example.figuremall_refact.controller.termController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.termDto.TermRequestDTO;
import com.example.figuremall_refact.dto.termDto.TermResponseDTO;
import com.example.figuremall_refact.service.termsService.TermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermController {

    private final TermService termService;

    @PostMapping
    public ApiResponse<TermResponseDTO.CreateTermResponseDto> createTerm(@Valid @RequestBody TermRequestDTO.CreateTermDto request) {
        return ApiResponse.onSuccess(termService.createTerm(request));
    }

    @PutMapping
    public ApiResponse<TermResponseDTO.CreateTermResponseDto> updateTerm(@Valid @RequestBody TermRequestDTO.UpdateTermDto request) {
        return ApiResponse.onSuccess(termService.updateTerm(request));
    }

    @DeleteMapping
    public ApiResponse<String> deleteTerm(@Valid @RequestBody TermRequestDTO.DeleteTermDto request) {
        termService.deleteTerm(request);
        return ApiResponse.onSuccess("이용 약관이 삭제되었습니다.");
    }

}
