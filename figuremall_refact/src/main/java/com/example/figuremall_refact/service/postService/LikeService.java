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
    private final UserService userService;
    private final PostService postService;

    public Like findById(Long id) {
        return likeRepository.findById(id).orElseThrow(() -> new PostHandler(ErrorStatus.LIKE_NOT_FOUND));
    }

    @Transactional
    public PostResponseDTO.LikeResponseDto addLike(PostRequestDTO.AddLikeDto request, String email) {
        User user = userService.findByEmail(email);
        Post post = postService.findById(request.getPostId());

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        return new PostResponseDTO.LikeResponseDto(likeRepository.save(like).getId());
    }

    @Transactional
    public void deleteLike(PostRequestDTO.DeleteLikeDto request) {
        likeRepository.deleteById(request.getLikeId());
    }

}
