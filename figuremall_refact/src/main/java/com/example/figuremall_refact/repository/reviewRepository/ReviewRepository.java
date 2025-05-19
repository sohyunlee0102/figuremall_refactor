package com.example.figuremall_refact.repository.reviewRepository;

import com.example.figuremall_refact.domain.review.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);
}
