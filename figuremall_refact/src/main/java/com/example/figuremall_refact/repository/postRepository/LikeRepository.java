package com.example.figuremall_refact.repository.postRepository;

import com.example.figuremall_refact.domain.post.Like;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsLikeByUserAndPost(User user, Post post);
    void deleteLikeByUserAndPost(User user, Post post);
}
