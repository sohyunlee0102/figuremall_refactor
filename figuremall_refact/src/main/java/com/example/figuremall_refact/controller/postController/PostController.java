package com.example.figuremall_refact.controller.postController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.service.postService.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponseDTO.AddPostResponseDto> addPost(@Valid @RequestPart PostRequestDTO.AddPostDto request,
                                                                   @RequestPart(required = false)MultipartFile[] files,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(postService.addPost(request, files, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<PostResponseDTO.AddPostResponseDto> editPost(@Valid @RequestPart PostRequestDTO.EditPostDto request,
                                                                    @RequestPart(required = false)MultipartFile[] files) {
        return ApiResponse.onSuccess(postService.editPost(request, files));
    }

    @DeleteMapping
    public ApiResponse<String> deletePost(@Valid @RequestBody PostRequestDTO.DeletePostDto request) {
        postService.deletePost(request.getPostId());
        return ApiResponse.onSuccess("게시글이 삭제되었습니다.");
    }

}
