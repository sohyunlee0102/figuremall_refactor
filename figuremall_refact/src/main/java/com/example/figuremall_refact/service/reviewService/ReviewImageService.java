package com.example.figuremall_refact.service.reviewService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.ReviewHandler;
import com.example.figuremall_refact.domain.review.Review;
import com.example.figuremall_refact.domain.review.ReviewImage;
import com.example.figuremall_refact.repository.reviewRepository.ReviewImageRepository;
import com.example.figuremall_refact.service.s3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;
    private final S3Service s3Service;

    public ReviewImage findById(Long id) {
        return reviewImageRepository.findById(id).orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_IMAGE_NOT_FOUND));
    }

    @Transactional
    public void addReviewImages(Review review, MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            try {
                String imageUrl = s3Service.uploadFile(file);

                ReviewImage reviewImage = ReviewImage.builder()
                        .review(review)
                        .imageUrl(imageUrl)
                        .build();

                reviewImageRepository.save(reviewImage);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }
    }

    @Transactional
    public void deleteReviewImages(List<Long> imageIds) {
        for (Long imageId : imageIds) {
            ReviewImage reviewImage = findById(imageId);
            s3Service.deleteFile(reviewImage.getImageUrl());
            reviewImageRepository.delete(reviewImage);
        }
    }

}
