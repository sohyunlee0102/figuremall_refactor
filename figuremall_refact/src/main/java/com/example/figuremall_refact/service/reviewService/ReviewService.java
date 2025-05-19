package com.example.figuremall_refact.service.reviewService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.ReviewHandler;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.review.Review;
import com.example.figuremall_refact.domain.review.ReviewImage;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.productDto.ProductRequestDTO;
import com.example.figuremall_refact.dto.reviewDto.ReviewRequestDTO;
import com.example.figuremall_refact.dto.reviewDto.ReviewResponseDTO;
import com.example.figuremall_refact.repository.reviewRepository.ReviewRepository;
import com.example.figuremall_refact.service.productService.ProductService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private final UserService userService;
    private final ProductService productService;

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND));
    }

    @Transactional
    public ReviewResponseDTO.AddReviewResponseDto addReview(ReviewRequestDTO.AddReviewDto request, String email, MultipartFile[] files) {
        User user = userService.findByEmail(email);
        Product product = productService.findProductById(request.getProductId());

        Review review = Review.builder()
                .user(user)
                .product(product)
                .content(request.getContent())
                .rating(request.getRating())
                .build();

        reviewRepository.save(review);
        reviewImageService.addReviewImages(review, files);

        return new ReviewResponseDTO.AddReviewResponseDto(review.getId());
    }

    @Transactional
    public ReviewResponseDTO.AddReviewResponseDto updateReview(ReviewRequestDTO.UpdateReviewDto request, String email, MultipartFile[] files) {
        Review review = findById(request.getReviewId());

        if (!review.getUser().getEmail().equals(email)) {
            throw new ReviewHandler(ErrorStatus.REVIEW_NOT_OWNED);
        }

        review.setContent(request.getContent());
        review.setRating(request.getRating());

        reviewImageService.addReviewImages(review, files);
        reviewImageService.deleteReviewImages(request.getDeleteImageIds());

        return new ReviewResponseDTO.AddReviewResponseDto(review.getId());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public List<ReviewResponseDTO.ProductReviews> getProductReviews(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        List<ReviewResponseDTO.ProductReviews> productReviews = new ArrayList<>();

        for (Review review : reviews) {
            List<ReviewResponseDTO.ReviewImages> images = new ArrayList<>();

            for (ReviewImage reviewImage : review.getImages()) {
                images.add(new ReviewResponseDTO.ReviewImages(reviewImage.getId(), reviewImage.getImageUrl()));
            }

            productReviews.add(new ReviewResponseDTO.ProductReviews(review.getId(), review.getUser().getId(),
                    review.getUser().getUsername(), review.getContent(), review.getRating(), images));
        }

        return productReviews;
    }

}
