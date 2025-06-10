package com.example.figuremall_refact.repository.postRepository;

import com.example.figuremall_refact.domain.enums.PostCategory;
import com.example.figuremall_refact.domain.post.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(PostCategory category, Pageable pageable);

    @Query(
            value = "SELECT p.id, p.category, p.content, p.created_at, p.title, p.updated_at, p.user_id, p.views " +
                    "FROM post p " +
                    "LEFT JOIN likes l ON p.id = l.post_id " +
                    "WHERE p.category = :category " +
                    "GROUP BY p.id, p.category, p.content, p.created_at, p.title, p.updated_at, p.user_id, p.views " +
                    "ORDER BY COUNT(l.id) DESC",
            countQuery = "SELECT COUNT(*) FROM post WHERE category = :category",
            nativeQuery = true
    )
    Page<Post> findByCategoryOrderByLikes(@Param("category") String category, Pageable pageable);


}
