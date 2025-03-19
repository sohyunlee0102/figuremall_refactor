package com.example.figuremall_refact.repository.reviewRepository;

import com.example.figuremall_refact.domain.review.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
