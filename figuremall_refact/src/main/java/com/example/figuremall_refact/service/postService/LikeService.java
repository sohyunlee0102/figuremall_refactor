package com.example.figuremall_refact.service.postService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PostHandler;
import com.example.figuremall_refact.domain.post.Like;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.postDto.PostRequestDTO;
import com.example.figuremall_refact.dto.postDto.PostResponseDTO;
import com.example.figuremall_refact.repository.postRepository.LikeRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public Like findById(Long id) {
        return likeRepository.findById(id).orElseThrow(() -> new PostHandler(ErrorStatus.LIKE_NOT_FOUND));
    }

    @Transactional
    public void addLike(Post post, User user) {
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(Post post, User user) {
        likeRepository.deleteLikeByUserAndPost(user, post);
    }

    @Transactional
    public boolean isLiked(User user, Post post) {
        return likeRepository.existsLikeByUserAndPost(user, post);
    }

}
