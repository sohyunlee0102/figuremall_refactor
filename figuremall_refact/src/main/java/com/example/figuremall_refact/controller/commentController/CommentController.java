package com.example.figuremall_refact.controller.commentController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.commentDto.CommentRequestDTO;
import com.example.figuremall_refact.dto.commentDto.CommentResponseDTO;
import com.example.figuremall_refact.service.commentService.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponseDTO.AddCommentResponseDto> addComment(@Valid @RequestBody CommentRequestDTO.AddCommentDto request,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(commentService.addComment(request, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<CommentResponseDTO.AddCommentResponseDto> editComment(@Valid @RequestBody CommentRequestDTO.EditCommentDto request) {
        return ApiResponse.onSuccess(commentService.editComment(request));
    }

    @DeleteMapping
    public ApiResponse<String> deleteComment(@Valid @RequestBody CommentRequestDTO.DeleteCommentDto request) {
        commentService.deleteComment(request);
        return ApiResponse.onSuccess("댓글이 삭제되었습니다.");
    }

}
