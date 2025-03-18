package com.example.figuremall_refact.controller.reviewController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.reviewDto.ReviewRequestDTO;
import com.example.figuremall_refact.dto.reviewDto.ReviewResponseDTO;
import com.example.figuremall_refact.service.reviewService.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponseDTO.AddReviewResponseDto> addReview(@Valid @RequestPart ReviewRequestDTO.AddReviewDto request,
                                                                         @RequestPart MultipartFile[] files,
                                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(reviewService.addReview(request, userDetails.getUsername(), files));
    }

    @PutMapping
    public ApiResponse<ReviewResponseDTO.AddReviewResponseDto> updateReview(@Valid @RequestPart ReviewRequestDTO.UpdateReviewDto request,
                                                                            @RequestPart MultipartFile[] newFiles,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(reviewService.updateReview(request, userDetails.getUsername(), newFiles));
    }

    @DeleteMapping
    public ApiResponse<String> deleteReview(@Valid @RequestBody ReviewRequestDTO.DeleteReviewDto request) {
        reviewService.deleteReview(request.getReviewId());
        return ApiResponse.onSuccess("리뷰가 삭제되었습니다.");
    }

}
