package com.example.figuremall_refact.service.noticeService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.NoticeHandler;
import com.example.figuremall_refact.domain.notice.Notice;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.noticeDto.NoticeRequestDTO;
import com.example.figuremall_refact.dto.noticeDto.NoticeResponseDTO;
import com.example.figuremall_refact.repository.noticeRepository.NoticeRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserService userService;

    public Notice findById(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeHandler(ErrorStatus.NOTICE_NOT_FOUND));
    }

    @Transactional
    public NoticeResponseDTO.NoticeDto createNotice(NoticeRequestDTO.CreateNoticeDTO request, String email) {
        User user = userService.findByEmail(email);

        Notice notice = Notice.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return new NoticeResponseDTO.NoticeDto(noticeRepository.save(notice).getId());
    }

    @Transactional
    public NoticeResponseDTO.NoticeDto updateNotice(NoticeRequestDTO.UpdateNoticeDTO request) {
        Notice notice = findById(request.getNoticeId());

        if (request.getTitle() != null) {
            notice.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            notice.setContent(request.getContent());
        }

        return new NoticeResponseDTO.NoticeDto(notice.getId());
    }

    @Transactional
    public void deleteNotice(NoticeRequestDTO.DeleteNoticeDTO request) {
        noticeRepository.deleteById(request.getNoticeId());
    }

}