package com.example.figuremall_refact.controller.postController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.service.postService.LikeService;
import com.example.figuremall_refact.service.postService.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping
    public ApiResponse<PostResponseDTO.AddPostResponseDto> addPost(@Valid @RequestPart(name = "request") PostRequestDTO.AddPostDto request,
                                                                   @RequestPart(name = "files", required = false)MultipartFile[] files,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(postService.addPost(request, files, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<PostResponseDTO.AddPostResponseDto> editPost(@Valid @RequestPart(name = "request") PostRequestDTO.EditPostDto request,
                                                                    @RequestPart(name = "files", required = false)MultipartFile[] files) {
        return ApiResponse.onSuccess(postService.editPost(request, files));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<String> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시글이 삭제되었습니다.");
    }

    @PostMapping("/likes")
    public ApiResponse<PostResponseDTO.LikeResponseDto> addLike(@Valid @RequestBody PostRequestDTO.AddLikeDto request,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(likeService.addLike(request, userDetails.getUsername()));
    }

    @DeleteMapping("/likes")
    public ApiResponse<String> deleteLike(@Valid @RequestBody PostRequestDTO.DeleteLikeDto request) {
        likeService.deleteLike(request);
        return ApiResponse.onSuccess("좋아요가 취소되었습니다.");
    }

    @GetMapping
    public ApiResponse<Page<PostResponseDTO.PostList>> getPosts(@RequestParam(name = "category", defaultValue = "community") String category,
                                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "10") int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {
        return ApiResponse.onSuccess(postService.getPosts(category, page, size, sortBy));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostResponse> getPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.getPost(postId));
    }

}
