package com.example.figuremall_refact.controller.noticeController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.noticeDto.NoticeRequestDTO;
import com.example.figuremall_refact.dto.noticeDto.NoticeResponseDTO;
import com.example.figuremall_refact.service.noticeService.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ApiResponse<NoticeResponseDTO.NoticeDto> createNotice(@Valid @RequestBody NoticeRequestDTO.CreateNoticeDTO request,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(noticeService.createNotice(request, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<NoticeResponseDTO.NoticeDto> updateNotice(@Valid @RequestBody NoticeRequestDTO.UpdateNoticeDTO request) {
        return ApiResponse.onSuccess(noticeService.updateNotice(request));
    }

    @DeleteMapping
    ApiResponse<String> deleteNotice(@Valid @RequestBody NoticeRequestDTO.DeleteNoticeDTO request) {
        return ApiResponse.onSuccess("공지가 삭제되었습니다.");
    }

}
