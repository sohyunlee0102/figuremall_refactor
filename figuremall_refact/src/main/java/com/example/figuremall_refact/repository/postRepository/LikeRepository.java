package com.example.figuremall_refact.repository.postRepository;

import com.example.figuremall_refact.domain.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
