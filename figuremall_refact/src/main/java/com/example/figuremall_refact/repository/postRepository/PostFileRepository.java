package com.example.figuremall_refact.repository.postRepository;

import com.example.figuremall_refact.domain.post.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
