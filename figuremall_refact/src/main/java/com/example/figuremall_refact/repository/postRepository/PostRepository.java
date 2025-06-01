package com.example.figuremall_refact.repository.postRepository;

import com.example.figuremall_refact.domain.enums.PostCategory;
import com.example.figuremall_refact.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(PostCategory category, Pageable pageable);
}
