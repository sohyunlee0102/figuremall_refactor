package com.example.figuremall_refact.controller.iceRinkController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.iceRinkDto.IceRinkRequestDTO;
import com.example.figuremall_refact.dto.iceRinkDto.IceRinkResponseDTO;
import com.example.figuremall_refact.service.iceRinkService.IceRinkSelectionService;
import com.example.figuremall_refact.service.iceRinkService.IceRinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/iceRinks")
public class IceRinkController {

    private final IceRinkService iceRinkService;
    private final IceRinkSelectionService iceRinkSelectionService;

    @PostMapping
    public ApiResponse<IceRinkResponseDTO> addIceRink(@Valid @RequestBody IceRinkRequestDTO.AddIceRinkDto request) {
        return ApiResponse.onSuccess(iceRinkService.createIceRink(request));
    }

    @PostMapping
    public ApiResponse<IceRinkResponseDTO> updateIceRink(@Valid @RequestBody IceRinkRequestDTO.UpdateIceRinkDto request) {
        return ApiResponse.onSuccess(iceRinkService.updateIceRink(request));
    }

    @DeleteMapping
    public ApiResponse<String> deleteIceRink(@Valid @RequestBody IceRinkRequestDTO.DeleteIceRinkDto request) {
        iceRinkService.deleteIceRink(request);
        return ApiResponse.onSuccess("빙상장이 삭제되었습니다.");
    }

    @PostMapping("/selections")
    public ApiResponse<String> addIceRinkSelections(@Valid @RequestBody IceRinkRequestDTO.AddIceRinkSelectionDto request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        iceRinkSelectionService.addIceRinkSelection(request, userDetails.getUsername());
        return ApiResponse.onSuccess("빙상장 선택이 추가되었습니다.");
    }

    @PutMapping("/selections")
    public ApiResponse<String> updateIceRinkSelections(@Valid @RequestBody IceRinkRequestDTO.AddIceRinkSelectionDto request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        iceRinkSelectionService.updateIceRinkSelection(request, userDetails.getUsername());
        return ApiResponse.onSuccess("빙상장 선택이 수정되었습니다.");
    }

}
