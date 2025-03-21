package com.example.figuremall_refact.service.commentService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.CommentHandler;
import com.example.figuremall_refact.domain.comment.Comment;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.commentDto.CommentRequestDTO;
import com.example.figuremall_refact.dto.commentDto.CommentResponseDTO;
import com.example.figuremall_refact.repository.commentRepository.CommentRepository;
import com.example.figuremall_refact.service.postService.PostService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));
    }

    @Transactional
    public CommentResponseDTO.AddCommentResponseDto addComment(CommentRequestDTO.AddCommentDto request, String email) {
        User user = userService.findByEmail(email);
        Post post = postService.findById(request.getPostId());
        Comment parent = null;

        if (request.getParentId() != null) {
            parent = findById(request.getParentId());
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .likes(0)
                .parent(parent)
                .build();

        return new CommentResponseDTO.AddCommentResponseDto(commentRepository.save(comment).getId());
    }

    @Transactional
    public CommentResponseDTO.AddCommentResponseDto editComment(CommentRequestDTO.EditCommentDto request) {
        Comment comment = findById(request.getCommentId());
        comment.setContent(request.getContent());

        return new CommentResponseDTO.AddCommentResponseDto(comment.getId());
    }

    @Transactional
    public void deleteComment(CommentRequestDTO.DeleteCommentDto request) {
        commentRepository.deleteById(request.getCommentId());
    }

}
