package com.example.figuremall_refact.repository.commentRepository;

import com.example.figuremall_refact.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
