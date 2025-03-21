package com.example.figuremall_refact.service.reportService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.ReportHandler;
import com.example.figuremall_refact.domain.comment.Comment;
import com.example.figuremall_refact.domain.enums.ReportType;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.report.Report;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.reportDto.ReportRequestDTO;
import com.example.figuremall_refact.dto.reportDto.ReportResponseDTO;
import com.example.figuremall_refact.repository.reportRepostiory.ReportRepository;
import com.example.figuremall_refact.service.commentService.CommentService;
import com.example.figuremall_refact.service.postService.PostService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ReportHandler(ErrorStatus.REPORT_NOT_FOUND));
    }

    @Transactional
    public ReportResponseDTO.CreateReportResponseDto createReport(ReportRequestDTO.CreateReportDto request, String email) {
        User user = userService.findByEmail(email);
        Comment comment = null;
        Post post = null;

        if (request.getReportType() == ReportType.COMMENT) {
            comment = commentService.findById(request.getCommentId());
        }

        if (request.getReportType() == ReportType.POST) {
            post = postService.findById(request.getPostId());
        }

        Report report = Report.builder()
                .reportType(request.getReportType())
                .reportReason(request.getReportReason())
                .details(request.getDetails())
                .reporter(user)
                .comment(comment)
                .post(post)
                .build();

        return new ReportResponseDTO.CreateReportResponseDto(reportRepository.save(report).getId());
    }

}
